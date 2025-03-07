package com.example.solarease.domain.usecases

import com.example.solarease.domain.repository.WeatherRepository
import javax.inject.Inject

class GetSolarWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getSolarWeather(lat, lon)
}
