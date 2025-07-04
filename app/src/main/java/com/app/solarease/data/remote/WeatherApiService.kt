package com.app.solarease.data.remote

import com.app.solarease.common.Constants
import com.app.solarease.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude")        latitude: Double,
        @Query("longitude")       longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("hourly")          hourly: String = Constants.WEATHER_HOURLY_PARAMS,
        @Query("daily")           daily: String = Constants.WEATHER_DAILY_PARAMS,
        @Query("timezone")        timezone: String = "auto"
    ): WeatherResponse
}
