package com.app.solarease.presentation.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.usecase.auth.GetCurrentUserUseCase
import com.app.solarease.domain.usecase.auth.SignInWithGoogleUseCase
import com.app.solarease.domain.usecase.auth.SignOutUseCase
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Checking)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _authState.value = try {
                getCurrentUserUseCase().firstOrNull()?.let { AuthState.Authenticated(it) }
                    ?: AuthState.Unauthenticated
            } catch (e: Exception) {
                AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signInWithGoogle(context: Context, webClientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _authState.value = AuthState.Checking
            try {
                withTimeout(100_000) {
                    val activity = context as? Activity
                        ?: throw IllegalStateException("Need Activity context for Google Sign-In")
                    val manager = CredentialManager.create(context)
                    val option = GetGoogleIdOption.Builder()
                        .setServerClientId(webClientId)
                        .setFilterByAuthorizedAccounts(false)
                        .setNonce(UUID.randomUUID().toString())
                        .build()
                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(option)
                        .build()
                    Log.d("AuthFlow", "Starting credential request...")
                    val result = try {
                        manager.getCredential(activity, request)
                    } catch (e: Exception) {
                        Log.e("AuthFlow", "Credential request failed", e)
                        throw e
                    }
                    val cred = result.credential
                    if (cred is GoogleIdTokenCredential) {
                        val token = cred.idToken.takeIf { it.isNotEmpty() }
                            ?: throw IllegalArgumentException("Invalid Google token")
                        when (val res = signInWithGoogleUseCase(token)) {
                            is Resource.Success ->
                                _authState.value = AuthState.Authenticated(res.data)
                            is Resource.Error ->
                                _authState.value = AuthState.Error(res.message)
                            is Resource.Loading ->
                                _authState.value = AuthState.Checking
                        }
                    } else {
                        _authState.value = AuthState.Error("Invalid credential type")
                    }
                }
            } catch (e: TimeoutCancellationException) {
                _authState.value = AuthState.Error("Connection timed out")
            } catch (e: GetCredentialCancellationException) {
                _authState.value = AuthState.Error("Authentication cancelled")
            } catch (e: GetCredentialException) {
                _authState.value = AuthState.Error("Authentication error: ${e.errorMessage}")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                signOutUseCase()
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign-out failed")
            } finally {
                _isLoading.value = false
            }
        }
    }

}