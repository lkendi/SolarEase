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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.DailyWeather
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.Sun
import compose.icons.tablericons.Sunset
import compose.icons.tablericons.Sunshine
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SunTimingCard(w: Weather) {
    val fmt = DateTimeFormatter.ofPattern("HH:mm")
    val firstDay = w.daily.firstOrNull()

    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(
                Triple(TablerIcons.Sunshine, "Sunrise", firstDay?.sunrise?.format(fmt) ?: "--"),
                Triple(TablerIcons.Sunset, "Sunset", firstDay?.sunset?.format(fmt) ?: "--"),
                Triple(TablerIcons.Sun, "UV Index", firstDay?.uvMax?.toInt()?.toString() ?: "--")
            ).forEach { (icon, label, value) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = SolarYellow,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = value,
                        style = Typography.labelLarge,
                        color = White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        style = Typography.labelMedium,
                        color = White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSunTimingCard() {
    val now = LocalDate.now()
    val mockWeather = Weather(
        current = CurrentWeather(
            time = ZonedDateTime.now(),
            weatherCode = 0,
            description = "Overcast",
            icon = TablerIcons.Cloud
        ),
        hourly = emptyList(),
        daily = listOf(
            DailyWeather(
                date = now,
                sunrise = ZonedDateTime.now().withHour(6).withMinute(15),
                sunset = ZonedDateTime.now().withHour(18).withMinute(45),
                uvMax = 7.8,
                precipitationProbMax = 20.0
            )
        )
    )

    SolarEaseTheme {
        SunTimingCard(w = mockWeather)
    }
}
