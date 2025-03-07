package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun
import java.time.Instant


@Composable
fun DailyForecastCard(
    day: String,
    sunrise: String,
    sunset: String,
    radiation: Float,
    precipitation: Float
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkGrey.copy(alpha = 0.2f)),
        modifier = Modifier.width(140.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)

        ) {
            Text(
                day,
                style = Typography.titleSmall,
                color = Yellow,
                modifier = Modifier.padding(bottom = 8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                WeatherIcon(precipitation = precipitation)
                Column {
                    Text(
                        "☀️ ${sunrise.substring(0,5)}",
                        style = Typography.labelSmall,
                        color = White.copy(alpha = 0.8f))

                    Text(
                        "🌙 ${sunset.substring(0,5)}",
                        style = Typography.labelSmall,
                        color = White.copy(alpha = 0.8f))
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SolarMetricChip(
                    text = "${radiation.toInt()}W",
                    color = Yellow,
                    icon = TablerIcons.Sun)

                SolarMetricChip(
                    text = "${precipitation.toInt()}mm",
                    color = SolarBlue,
                    icon = TablerIcons.CloudRain)
            }
        }
    }
}

@Preview
@Composable
fun DailyForecastCardPreview() {
    SolarEaseTheme {
        DailyForecastCard(day = " Friday", sunrise = Instant.now().toString(), sunset = Instant.now().toString(), radiation = 20f, precipitation = 20f)
    }
}