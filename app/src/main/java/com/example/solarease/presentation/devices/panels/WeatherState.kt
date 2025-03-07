package com.example.solarease.presentation.devices.panels

import com.example.solarease.domain.model.weather.WeatherResponse

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
