package com.app.solarease.presentation.devices.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.HourlyWeather
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun
import java.time.ZonedDateTime

@Composable
fun CurrentConditionsCard(w: Weather) {
    val currentHour = w.current.time.hour
    val hourlyMatch = w.hourly.firstOrNull { it.time.hour == currentHour } ?: w.hourly.firstOrNull()

    val cloudCover = hourlyMatch?.cloudCover?.let { "${(it * 100).toInt()}%" } ?: "--"
    val precipitation = hourlyMatch?.precipitationProb?.let { "${it.toInt()}" } ?: "--"
    val radiation = hourlyMatch?.radiation?.let { "${it.toInt()} W/m²" } ?: "--"

    val items = listOf(
        Triple("Overall Weather", w.current.description, w.current.icon),
        Triple("Cloud Cover", cloudCover, TablerIcons.Cloud),
        Triple("Rain Chance", precipitation, TablerIcons.CloudRain),
        Triple("Radiation", radiation, TablerIcons.Sun)
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Current Conditions",
                style = Typography.bodyMedium,
                color = SolarYellow
            )
            Spacer(Modifier.height(16.dp))
            items.chunked(2).forEach { rowItems ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { (label, value, icon) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = SolarYellow,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.height(6.dp))
                            if (value.isNotEmpty()) {
                                Text(text = value, style = Typography.labelLarge, color = White)
                                Spacer(Modifier.height(2.dp))
                            }
                            Text(text = label, style = Typography.labelMedium, color = White.copy(alpha = .7f))
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentConditionsCard() {
    val now = ZonedDateTime.now()
    val mockWeather = Weather(
        current = CurrentWeather(
            time = now,
            weatherCode = 3,
            description = "Overcast",
            icon = TablerIcons.Cloud
        ),
        hourly = listOf(
            HourlyWeather(
                time = now,
                radiation = 500.0,
                cloudCover = 0.6,
                precipitationProb = 20.0
            )
        ),
        daily = emptyList()
    )

    CurrentConditionsCard(w = mockWeather)
}
