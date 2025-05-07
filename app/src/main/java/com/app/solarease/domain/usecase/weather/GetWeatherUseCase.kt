package com.app.solarease.domain.usecase.weather

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Weather
import com.app.solarease.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repo: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double, timezone: String): Resource<Weather> =
        repo.getWeather(lat, lon, timezone)
}
