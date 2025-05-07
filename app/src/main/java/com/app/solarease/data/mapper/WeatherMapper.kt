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

private val ISO_LOCAL = DateTimeFormatter.ISO_LOCAL_DATE_TIME

fun WeatherResponse.toDomain(): Weather {
    val zone = ZoneId.of(timezone)

    return Weather(
        current = mapCurrentWeather(zone),
        hourly = mapHourlyData(zone),
        daily = mapDailyData(zone)
    )
}

private fun WeatherResponse.mapCurrentWeather(zone: ZoneId): CurrentWeather {
    val currentZdt = LocalDateTime.parse(current.time, ISO_LOCAL).atZone(zone)
    val codeEntry = WeatherCodeMapper.forCode(current.weatherCode)

    return CurrentWeather(
        time = currentZdt,
        weatherCode = current.weatherCode,
        description = codeEntry.description,
        icon = codeEntry.icon
    )
}

private fun WeatherResponse.mapHourlyData(zone: ZoneId): List<HourlyWeather> {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    return hourly.times.mapIndexed { i, t ->
        val localDateTime = LocalDateTime.parse(t, formatter)
        val zonedDateTime = localDateTime.atZone(zone)

        HourlyWeather(
            time = zonedDateTime,
            radiation = hourly.radiations.getOrNull(i) ?: 0.0,
            cloudCover = hourly.cloudCovers.getOrNull(i),
            precipitationProb = hourly.precipitationProbability.getOrNull(i)
        )
    }
}


private fun WeatherResponse.mapDailyData(zone: ZoneId): List<DailyWeather> {
    val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    return daily.dates.mapIndexed { i, date ->
        DailyWeather(
            date = LocalDate.parse(date, dateFormatter),
            sunrise = LocalDateTime.parse(daily.sunrise[i], dateTimeFormatter).atZone(zone),
            sunset = LocalDateTime.parse(daily.sunset[i], dateTimeFormatter).atZone(zone),
            uvMax = daily.uvMax.getOrNull(i),
            precipitationProbMax = daily.precipitationProbabilityMax.getOrNull(i)
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
