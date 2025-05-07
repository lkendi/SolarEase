package com.app.solarease.data.repository

import com.app.solarease.common.Constants
import com.app.solarease.common.Resource
import com.app.solarease.data.mapper.toDomain
import com.app.solarease.data.remote.WeatherApiService
import com.app.solarease.domain.model.Weather
import com.app.solarease.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double, timezone: String): Resource<Weather> {
        return try {
            val resp = api.getWeather(
                latitude = lat,
                longitude = lon,
                hourly = Constants.HOURLY_PARAMS,
                daily = Constants.DAILY_PARAMS
            )
            Resource.Success(resp.toDomain())
        } catch (t: Throwable) {
            Resource.Error(t.message ?: "Unknown error")
        }
    }
}
