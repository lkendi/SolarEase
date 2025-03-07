package com.example.solarease.domain.repository

import com.example.solarease.domain.model.weather.WeatherResponse
import com.example.solarease.util.Resource

interface WeatherRepository {
    suspend fun getSolarWeather(lat: Double, lon: Double): Resource<WeatherResponse>
}