package com.app.solarease.presentation.devices.panels

import com.app.solarease.domain.model.Weather

data class WeatherState(
    val data: Weather? = null,
    val loading: Boolean = false,
    val error: String? = null
)
