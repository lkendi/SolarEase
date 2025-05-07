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
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Rainbow
import java.time.ZonedDateTime

@Composable
fun WeatherCard(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    val weatherCodeEntry = currentWeather.let {
        WeatherCodeMapper.forCode(it.weatherCode)
    }

    val gradient = when(weatherCodeEntry.description.lowercase()) {
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
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = weatherCodeEntry.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "220 KW",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Text(
                            text = "Today's production",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                    Icon(
                        imageVector = weatherCodeEntry.icon,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun WeatherCardPreview() {
    SolarEaseTheme {
        WeatherCard(
            currentWeather = CurrentWeather(
                weatherCode = 0,
                time = ZonedDateTime.now(),
                description = "Partly cloudy",
                icon = TablerIcons.Rainbow
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

