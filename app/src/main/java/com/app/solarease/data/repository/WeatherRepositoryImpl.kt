package com.app.solarease.data.repository

import com.app.solarease.common.Resource
import com.app.solarease.data.cache.WeatherCache
import com.app.solarease.data.mapper.toDomain
import com.app.solarease.data.remote.WeatherApiService
import com.app.solarease.domain.model.Weather
import com.app.solarease.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val cache: WeatherCache
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, forceRefresh: Boolean): Resource<Weather> {
        return try {
            if (!forceRefresh) {
                cache.getWeather()?.let {
                    if (cache.isValid()) return Resource.Success(it)
                }
            }

            val response = api.getWeather(lat, lon)
            val weather = response.toDomain()
            cache.saveWeather(weather)
            Resource.Success(weather)
        } catch (e: Exception) {
            cache.getWeather()?.let { Resource.Success(it) }
                ?: Resource.Error(e.message ?: "Unknown error")
        }
    }
}
