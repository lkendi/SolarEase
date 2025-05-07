package com.app.solarease.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "current_weather") val current: WeatherCurrent,
    val hourly: WeatherHourly,
    val daily: WeatherDaily,
    @Json(name = "timezone") val timezone: String
)

data class WeatherCurrent(
    val time: String,
    @Json(name = "weathercode") val weatherCode: Int,
)

data class WeatherHourly(
    @Json(name = "time") val times: List<String>,
    @Json(name = "shortwave_radiation") val radiations: List<Double?>,
    @Json(name = "cloud_cover") val cloudCovers: List<Double?>,
    @Json(name = "precipitation_probability") val precipitationProbability: List<Double?>
)

data class WeatherDaily(
    @Json(name = "sunrise") val sunrise: List<String>,
    @Json(name = "sunset") val sunset: List<String>,
    @Json(name = "uv_index_max") val uvMax: List<Double?>,
    @Json(name = "precipitation_probability_max") val precipitationProbabilityMax: List<Double?>,
    @Json(name = "time") val dates: List<String>
)
