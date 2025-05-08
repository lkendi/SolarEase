package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, forceRefresh: Boolean): Resource<Weather>
}
