package com.app.solarease.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate
import java.time.ZonedDateTime

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val time: ZonedDateTime,
    val weatherCode: Int,
    val description: String?,
    val icon: ImageVector?,
)

data class HourlyWeather(
    val time: ZonedDateTime,
    val radiation: Double,
    val temperature: Double?,
    val cloudCover: Double?,
    val precipitationProb: Double?
)

data class DailyWeather(
    val date: LocalDate,
    val sunrise: ZonedDateTime,
    val sunset: ZonedDateTime,
    val radiationSum: Double?,
    val sunshineDuration: Double?,
    val precipitationProbMax: Double?
)
