package com.example.solarease.data.remote

import com.example.solarease.domain.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getSolarWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") currentParams: String = "temperature_2m,cloud_cover,precipitation,shortwave_radiation,sunshine_duration",
        @Query("hourly") hourlyParams: String = "shortwave_radiation,cloud_cover",
        @Query("daily") dailyParams: String = "sunrise,sunset,precipitation_sum,shortwave_radiation_sum",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}
