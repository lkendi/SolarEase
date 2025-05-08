package com.app.solarease.data.cache

import com.app.solarease.domain.model.Weather
import javax.inject.Singleton

@Singleton
class WeatherCache {
    private var cachedWeather: Weather? = null
    private var lastUpdated: Long = 0

    fun saveWeather(weather: Weather) {
        cachedWeather = weather
        lastUpdated = System.currentTimeMillis()
    }

    fun getWeather(): Weather? {
        return if (System.currentTimeMillis() - lastUpdated < CACHE_DURATION) {
            cachedWeather
        } else {
            null
        }
    }

    fun isValid(): Boolean {
        return System.currentTimeMillis() - lastUpdated < CACHE_DURATION
    }

    companion object {
        private const val CACHE_DURATION = 30 * 60 * 1000
    }
}
