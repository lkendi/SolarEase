package com.app.solarease.data.mapper

import androidx.compose.ui.graphics.vector.ImageVector
import com.app.solarease.data.model.WeatherResponse
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.DailyWeather
import com.app.solarease.domain.model.HourlyWeather
import com.app.solarease.domain.model.Weather
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.InfoCircle
import compose.icons.tablericons.Snowflake
import compose.icons.tablericons.Sun
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val ISO_DATETIME = DateTimeFormatter.ISO_DATE_TIME
private val ISO_DATE     = DateTimeFormatter.ISO_LOCAL_DATE

fun WeatherResponse.toDomain(): Weather {
    val zone = ZoneId.of(timezone)
    return Weather(
        current = mapCurrentWeather(zone),
        hourly  = mapHourlyWeather(zone),
        daily   = mapDailyWeather(zone)
    )
}

private fun WeatherResponse.mapCurrentWeather(zone: ZoneId): CurrentWeather {
    val zdt = LocalDateTime.parse(current.time, ISO_DATETIME).atZone(zone)
    return CurrentWeather(
        time = zdt, weatherCode = current.weatherCode,
        description = null,
        icon = null
    )
}

private fun WeatherResponse.mapHourlyWeather(zone: ZoneId): List<HourlyWeather> {
    return hourly.time.mapIndexed { i, t ->
        val zdt = LocalDateTime.parse(t, ISO_DATETIME).atZone(zone)
        HourlyWeather(
            time = zdt,
            radiation = hourly.shortwave.getOrNull(i) ?: 0.0,
            cloudCover = hourly.cloudCover.getOrNull(i),
            temperature = hourly.temperature.getOrNull(i),
            precipitationProb = hourly.precipitationProb.getOrNull(i)
        )
    }
}

private fun WeatherResponse.mapDailyWeather(zone: ZoneId): List<DailyWeather> {
    return daily.time.mapIndexed { i, d ->
        val date = LocalDate.parse(d, ISO_DATE)
        val srZdt = LocalDateTime.parse(daily.sunrise[i], ISO_DATETIME).atZone(zone)
        val ssZdt = LocalDateTime.parse(daily.sunset[i], ISO_DATETIME).atZone(zone)
        DailyWeather(
            date = date,
            sunrise = srZdt,
            sunset = ssZdt,
            radiationSum = daily.radiationSum.getOrNull(i),
            sunshineDuration = daily.sunshineDuration.getOrNull(i),
            precipitationProbMax = daily.precipitationMax.getOrNull(i)
        )
    }
}

object WeatherCodeMapper {
    data class Entry(val description: String, val icon: ImageVector)

    private val map = mapOf(
        0   to Entry("Clear sky",                  TablerIcons.Sun),
        1   to Entry("Mainly clear",               TablerIcons.Sun),
        2   to Entry("Partly cloudy",              TablerIcons.Sun),
        3   to Entry("Overcast",                   TablerIcons.Cloud),
        45  to Entry("Fog",                        TablerIcons.Cloud),
        48  to Entry("Rime fog",                   TablerIcons.Cloud),
        51  to Entry("Light drizzle",              TablerIcons.CloudRain),
        53  to Entry("Moderate drizzle",           TablerIcons.CloudRain),
        55  to Entry("Dense drizzle",              TablerIcons.CloudRain),
        56  to Entry("Light freezing drizzle",     TablerIcons.CloudRain),
        57  to Entry("Dense freezing drizzle",     TablerIcons.CloudRain),
        61  to Entry("Slight rain",                TablerIcons.CloudRain),
        63  to Entry("Moderate rain",              TablerIcons.CloudRain),
        65  to Entry("Heavy rain",                 TablerIcons.CloudRain),
        66  to Entry("Light freezing rain",        TablerIcons.CloudRain),
        67  to Entry("Heavy freezing rain",        TablerIcons.CloudRain),
        71  to Entry("Slight snow fall",           TablerIcons.Snowflake),
        73  to Entry("Moderate snow fall",         TablerIcons.Snowflake),
        75  to Entry("Heavy snow fall",            TablerIcons.Snowflake),
        77  to Entry("Snow grains",                TablerIcons.Snowflake),
        80  to Entry("Slight rain showers",        TablerIcons.CloudRain),
        81  to Entry("Moderate rain showers",      TablerIcons.CloudRain),
        82  to Entry("Heavy rain showers",         TablerIcons.CloudRain),
        85  to Entry("Slight snow showers",        TablerIcons.Snowflake),
        86  to Entry("Heavy snow showers",         TablerIcons.Snowflake),
        95  to Entry("Thunderstorm",               TablerIcons.Bolt),
        96  to Entry("Thunder + slight hail",      TablerIcons.Bolt),
        99  to Entry("Thunder + heavy hail",       TablerIcons.Bolt)
    )

    fun forCode(code: Int): Entry =
        map[code] ?: Entry("Unknown", TablerIcons.InfoCircle)
}
