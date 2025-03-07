package com.example.solarease.data.repository

import com.example.solarease.data.remote.WeatherApiService
import com.example.solarease.domain.model.weather.WeatherResponse
import com.example.solarease.domain.repository.WeatherRepository
import com.example.solarease.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getSolarWeather(lat: Double, lon: Double): Resource<WeatherResponse> {
        return try {
            val response = apiService.getSolarWeather(lat, lon)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch weather: ${e.message}")
        }
    }
}
