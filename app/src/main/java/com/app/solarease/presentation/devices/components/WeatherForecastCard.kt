package com.app.solarease.presentation.devices.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "3-Day Forecast",
                    style = Typography.titleMedium,
                    color = SolarYellow,
                    modifier = Modifier.weight(1f)
                )
            }

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
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, SolarBlue.copy(alpha = 0.2f)),
        modifier = Modifier.width(160.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column {
                Text(
                    text = day.date.format(DateTimeFormatter.ofPattern("EEE, d")),
                    style = Typography.labelMedium,
                    color = White.copy(alpha = 0.8f)
                )

                Spacer(Modifier.height(8.dp))

                WeatherMetric(
                    icon = TablerIcons.Sun,
                    value = "%.1fh".format((day.sunshineDuration ?: 0.0) / 3600),
                    label = "Sunlight",
                    color = SolarYellow,
                    progress = ((day.sunshineDuration ?: 0.0) / 24).toFloat().coerceIn(0f, 1f)
                )

                Spacer(Modifier.height(8.dp))

                WeatherMetric(
                    icon = TablerIcons.CloudRain,
                    value = "${day.precipitationProbMax?.toInt() ?: 0}%",
                    label = "Rain chance",
                    color = SolarBlue,
                    progress = (day.precipitationProbMax?.toFloat() ?: 0f) / 100f
                )

                Spacer(Modifier.height(8.dp))

                WeatherMetric(
                    icon = TablerIcons.Sunshine,
                    value = "${day.radiationSum?.toInt() ?: 0}",
                    label = "kWh/m² - Radiation",
                    color = SolarGreen,
                    progress = (day.radiationSum?.toFloat() ?: 0f) / 100f
                )
            }

            HorizontalDivider(thickness = 1.dp, color = White.copy(alpha = 0.1f))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TimeInfo(
                    icon = TablerIcons.Sunshine,
                    time = day.sunrise.format(DateTimeFormatter.ofPattern("HH:mm")),
                    label = "Sunrise"
                )
                TimeInfo(
                    icon = TablerIcons.Sunset,
                    time = day.sunset.format(DateTimeFormatter.ofPattern("HH:mm")),
                    label = "Sunset"
                )
            }
        }
    }
}

@Composable
private fun WeatherMetric(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    progress: Float
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = value,
                style = Typography.bodyMedium,
                color = color)
            Spacer(Modifier.width(4.dp))
            Text(
                text = label,
                style = Typography.labelSmall,
                color = White.copy(alpha = 0.6f))
        }

        Spacer(Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.1f),
        )
    }
}

@Composable
private fun TimeInfo(icon: ImageVector, time: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White.copy(alpha = 0.8f),
            modifier = Modifier.size(16.dp))
        Text(
            text = time,
            style = Typography.labelMedium,
            color = White)
        Text(
            text = label,
            style = Typography.labelSmall,
            color = White.copy(alpha = 0.6f))
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
            precipitationProbMax = 30.0,
            radiationSum = 45.7,
            sunshineDuration = 12.0
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
