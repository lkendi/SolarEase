package com.app.solarease.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.usecase.auth.GetCurrentUserUseCase
import com.app.solarease.domain.usecase.device.GetDevicesUseCase
import com.app.solarease.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getDevicesUseCase: GetDevicesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    sealed class InitState {
        data object Loading : InitState()
        data object Authenticated : InitState()
        data object Unauthenticated : InitState()
        data class Error(val message: String) : InitState()
    }

    val initState = MutableStateFlow<InitState>(InitState.Loading)

    init {
        initializeApp()
    }

    private fun initializeApp() = viewModelScope.launch {
        try {
            val user = getCurrentUserUseCase().firstOrNull()
            if (user != null) {
                val timezone = TimeZone.getDefault().id
                val weatherJob = async { getWeatherUseCase(DEFAULT_LAT, DEFAULT_LON, timezone, true) }
                val devicesJob = async { getDevicesUseCase(true) }
                val results = awaitAll(weatherJob, devicesJob)

                val weatherResult = results[0] as Resource<*>
                val devicesResult = results[1] as Resource<*>

                if (weatherResult is Resource.Success && devicesResult is Resource.Success) {
                    initState.value = InitState.Authenticated
                } else {
                    val errorMessage = buildString {
                        if (weatherResult is Resource.Error) append("Weather error: ${weatherResult.message}. ")
                        if (devicesResult is Resource.Error) append("Devices error: ${devicesResult.message}")
                    }
                    initState.value = InitState.Error(errorMessage.trim())
                }
            } else {
                initState.value = InitState.Unauthenticated
            }
        } catch (e: Exception) {
            initState.value = InitState.Error(e.message ?: "Failed to initialize app")
        }
    }

    companion object {
        private const val DEFAULT_LAT = -1.2921
        private const val DEFAULT_LON = 36.8219
    }
}
