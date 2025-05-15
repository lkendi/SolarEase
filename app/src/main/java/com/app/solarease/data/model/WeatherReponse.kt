package com.app.solarease.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "current_weather") val current: WeatherCurrent,
    val hourly: WeatherHourly,
    val daily: WeatherDaily,
    val timezone: String
)

data class WeatherCurrent(
    val time: String,
    @Json(name = "weathercode") val weatherCode: Int
)

data class WeatherHourly(
    val time: List<String>,
    @Json(name = "shortwave_radiation") val shortwave: List<Double?>,
    @Json(name = "cloud_cover") val cloudCover: List<Double?>,
    @Json(name = "temperature_2m") val temperature: List<Double?>,
    @Json(name = "precipitation_probability") val precipitationProb: List<Double?>
)

data class WeatherDaily(
    val time: List<String>,
    val sunrise: List<String>,
    val sunset: List<String>,
    @Json(name = "shortwave_radiation_sum") val radiationSum: List<Double?>,
    @Json(name = "sunshine_duration") val sunshineDuration: List<Double?>,
    @Json(name = "precipitation_probability_max") val precipitationMax: List<Double?>
)
