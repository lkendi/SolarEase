package com.app.solarease.presentation.devices.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.DailyWeather
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarGreen
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun
import compose.icons.tablericons.Sunset
import compose.icons.tablericons.Sunshine
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherForecastCard(w: Weather) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = .1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("3-Day Forecast", style = Typography.titleMedium, color = SolarYellow)
            Spacer(Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(w.daily.take(3)) { day ->
                    DailyForecastItem(day = day)
                }
            }
        }
    }
}

@Composable
private fun DailyForecastItem(day: DailyWeather) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(140.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SolarBlue.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = day.date.format(DateTimeFormatter.ofPattern("EEE")),
                    style = Typography.labelLarge,
                    color = White.copy(alpha = 0.8f)
                )

                Spacer(Modifier.height(8.dp))

                WeatherInfoRow(
                    icon = TablerIcons.CloudRain,
                    value = "${day.precipitationProbMax?.toInt() ?: 0}%",
                    color = SolarBlue
                )

                LinearProgressIndicator(
                    progress = { (day.precipitationProbMax?.toFloat() ?: 0f) / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = SolarBlue,
                    trackColor = White.copy(alpha = 0.1f),
                )
            }

            Column {
                WeatherInfoRow(
                    icon = TablerIcons.Sun,
                    value = "UV ${day.uvMax?.toInt() ?: 0}",
                    color = when (day.uvMax ?: 0.0) {
                        in 0.0..2.9 -> SolarGreen
                        in 3.0..5.9 -> SolarYellow
                        else -> SolarOrange
                    }
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherInfoItem(
                        icon = TablerIcons.Sunshine,
                        value = day.sunrise.format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
                    WeatherInfoItem(
                        icon = TablerIcons.Sunset,
                        value = day.sunset.format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherInfoRow(icon: ImageVector, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp))
        Text(
            text = value,
            style = Typography.labelMedium,
            color = color)
    }
}

@Composable
private fun WeatherInfoItem(icon: ImageVector, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White.copy(alpha = 0.8f),
            modifier = Modifier.size(16.dp))
        Text(
            text = value,
            style = Typography.labelSmall,
            color = White.copy(alpha = 0.8f))
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherForecastCardPreview() {
    val now = LocalDate.now()
    val sampleDaily = listOf(
        DailyWeather(
            date = now,
            sunrise = ZonedDateTime.now().withHour(6).withMinute(15),
            sunset = ZonedDateTime.now().withHour(18).withMinute(45),
            uvMax = 5.0,
            precipitationProbMax = 10.0
        ),
        DailyWeather(
            date = now.plusDays(1),
            sunrise = ZonedDateTime.now().plusDays(1).withHour(6).withMinute(20),
            sunset = ZonedDateTime.now().plusDays(1).withHour(18).withMinute(50),
            uvMax = 6.5,
            precipitationProbMax = 20.0
        ),
        DailyWeather(
            date = now.plusDays(2),
            sunrise = ZonedDateTime.now().plusDays(2).withHour(6).withMinute(10),
            sunset = ZonedDateTime.now().plusDays(2).withHour(18).withMinute(40),
            uvMax = 4.2,
            precipitationProbMax = 5.0
        )
    )

    val sampleWeather = Weather(
        current = CurrentWeather(
            time = ZonedDateTime.now(),
            weatherCode = 0,
            description = "",
            icon = TablerIcons.Sun
        ),
        hourly = emptyList(),
        daily = sampleDaily
    )

    SolarEaseTheme {
        WeatherForecastCard(w = sampleWeather)
    }
}
