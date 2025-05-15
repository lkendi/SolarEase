package com.app.solarease.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.data.mapper.WeatherCodeMapper
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.DailyWeather
import com.app.solarease.domain.model.HourlyWeather
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.Rainbow
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherCard(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    val currentWeather = weather.current
    val dailyWeather = weather.daily.firstOrNull()
    val weatherCodeEntry = currentWeather.let {
        WeatherCodeMapper.forCode(it.weatherCode)
    }

    val fmt = DateTimeFormatter.ofPattern("HH:mm")
    val sunrise = dailyWeather?.sunrise?.format(fmt) ?: "--"
    val sunset = dailyWeather?.sunset?.format(fmt) ?: "--"
    val currentHour = weather.current.time.hour
    val hourlyMatch = weather.hourly.firstOrNull { it.time.hour == currentHour } ?: weather.hourly.firstOrNull()
    val cloudCover = hourlyMatch?.cloudCover?.let { "${it.toInt()} %" } ?: "--"
    val sunshineDuration = "%.1fh".format((dailyWeather?.sunshineDuration ?: 0.0) / 3600)

    val gradient = when (weatherCodeEntry.description.lowercase()) {
        "clear sky" -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFFC107), Color(0xFFFF9800))
        )
        "rain" -> Brush.verticalGradient(
            colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2))
        )
        "cloudy" -> Brush.verticalGradient(
            colors = listOf(Color(0xFF78909C), Color(0xFF546E7A))
        )
        else -> Brush.verticalGradient(
            colors = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = weatherCodeEntry.description,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                        Text(
                            text = "20 KW",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Today's production",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                    Icon(
                        imageVector = weatherCodeEntry.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                HorizontalDivider(thickness = 0.5.dp, color = White.copy(alpha = 0.4f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WeatherDetailItem(sunrise, "Sunrise")
                    WeatherDetailItem(sunset, "Sunset")
                    WeatherDetailItem(sunshineDuration, "Sunshine")
                    WeatherDetailItem(cloudCover, "Cloud Cover")
                }

                Text(
                    text = "• Peak production expected in 2 hours",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Preview
@Composable
fun WeatherCardPreview() {
    val now = ZonedDateTime.now()
    val mockWeather = Weather(
        current = CurrentWeather(
            time = now,
            weatherCode = 0,
            description = "Clear sky",
            icon = TablerIcons.Rainbow
        ),
        hourly = listOf(
            HourlyWeather(
                time = now,
                cloudCover = 72.0,
                radiation = 20.0,
                precipitationProb = 10.1,
                temperature = 22.2
            )
        ),
        daily = listOf(
            DailyWeather(
                date = now.toLocalDate(),
                sunrise = now.withHour(6).withMinute(15),
                sunset = now.withHour(18).withMinute(45),
                precipitationProbMax = 10.7,
                radiationSum = 22.1,
                sunshineDuration = 1.2
            )
        )
    )

    SolarEaseTheme {
        WeatherCard(
            weather = mockWeather,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
