package com.app.solarease.presentation.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.LatLng
import com.app.solarease.domain.model.User
import com.app.solarease.domain.usecase.auth.GetCurrentUserUseCase
import com.app.solarease.domain.usecase.device.GetDevicesUseCase
import com.app.solarease.domain.usecase.location.CheckLocationPermissionUseCase
import com.app.solarease.domain.usecase.location.GetLocationUseCase
import com.app.solarease.domain.usecase.user.SaveUserUseCase
import com.app.solarease.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getDevicesUseCase: GetDevicesUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "AppViewModel"
    }

    private val _initState = MutableStateFlow<InitState>(InitState.Loading)
    val initState: StateFlow<InitState> = _initState

    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.Idle)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _ready = MutableStateFlow(false)
    val ready: StateFlow<Boolean> = _ready

    init {
        initializeAppState()
    }

    private fun initializeAppState() {
        viewModelScope.launch {
            try {
                val user = getCurrentUserUseCase().firstOrNull()
                if (user != null) {
                    _initState.value = InitState.Authenticated
                    handleAuthenticatedUser(user)
                } else {
                    _initState.value = InitState.Unauthenticated
                }
            } catch (e: Exception) {
                _initState.value = InitState.Error("Initialization failed: ${e.message}")
            }
        }
    }

    private suspend fun handleAuthenticatedUser(user: User) {
        checkLocationPermission()
        if (permissionState.value == PermissionState.Granted) {
            fetchEssentialData(user)
        }
    }

    private suspend fun checkLocationPermission() {
        when (val result = checkLocationPermissionUseCase()) {
            is Resource.Success -> {
                _permissionState.value = if (result.data) {
                    PermissionState.Granted
                } else {
                    PermissionState.RequestPermission
                }
            }
            is Resource.Error -> {
                _permissionState.value = PermissionState.RequestPermission
                Log.e(TAG, "Permission check failed: ${result.message}")
            }
            is Resource.Loading -> Unit
        }
    }

    fun handlePermissionResult(granted: Boolean) {
        viewModelScope.launch {
            _permissionState.value = if (granted) {
                try {
                    val user = getCurrentUserUseCase().firstOrNull()
                    user?.let { fetchEssentialData(it) }
                    PermissionState.Granted
                } catch (e: Exception) {
                    Log.e(TAG, "Permission handling failed", e)
                    PermissionState.Denied
                }
            } else {
                PermissionState.Denied
            }
        }
    }

    private suspend fun fetchEssentialData(user: User) {
        when (val location = getLocationUseCase()) {
            is Resource.Success -> {
                val updatedUser = user.copy(location = location.data)
                saveUserUseCase(updatedUser)
                fetchWeatherAndDevices(location.data, updatedUser)
            }
            is Resource.Error -> {
                _initState.value = InitState.Error("Location fetch failed: ${location.message}")
            }
            is Resource.Loading -> Unit
        }
    }

    private suspend fun fetchWeatherAndDevices(latLng: LatLng, user: User) {
        try {
            val timezone = TimeZone.getDefault().id
            val (weatherResult, devicesResult) = coroutineScope {
                awaitAll(
                    async { getWeatherUseCase(latLng.latitude, latLng.longitude, timezone, true) },
                    async { getDevicesUseCase(true) }
                )
            }

            if (weatherResult is Resource.Success && devicesResult is Resource.Success) {
                _ready.value = true
                _initState.value = InitState.Authenticated
            } else {
                handleFetchErrors(weatherResult, devicesResult)
            }
        } catch (e: Exception) {
            _initState.value = InitState.Error("Data fetch failed: ${e.message}")
            Log.e(TAG, "Data fetch error", e)
        }
    }

    private fun handleFetchErrors(
        weatherResult: Resource<*>,
        devicesResult: Resource<*>
    ) {
        val errorMessage = buildString {
            if (weatherResult is Resource.Error) append("Weather: ${weatherResult.message}")
            if (devicesResult is Resource.Error) append("Devices: ${devicesResult.message}")
        }
        _initState.value = InitState.Error(errorMessage)
        Log.e(TAG, "Data fetch failed: $errorMessage")
    }
}
