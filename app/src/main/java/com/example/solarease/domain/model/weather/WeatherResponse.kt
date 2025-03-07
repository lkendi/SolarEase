package com.example.solarease.domain.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather,
    val hourly: HourlyData,
    val daily: DailyData
)

data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Float,
    @SerializedName("cloud_cover") val cloudCover: Int,
    @SerializedName("precipitation") val precipitation: Float,
    @SerializedName("shortwave_radiation") val solarRadiation: Float,
    @SerializedName("sunshine_duration") val sunshineDuration: Float
)

data class HourlyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("shortwave_radiation") val solarRadiation: List<Float>,
    @SerializedName("cloud_cover") val cloudCover: List<Int>
)

data class DailyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("sunrise") val sunrise: List<String>,
    @SerializedName("sunset") val sunset: List<String>,
    @SerializedName("shortwave_radiation_sum") val solarRadiation: List<Float>,
    @SerializedName("precipitation_sum") val precipitation: List<Float>,
)
