package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.weather.DailyData
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter



@Composable
fun DailySolarForecast(daily: DailyData) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkGrey.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "7-Day Forecast",
                style = Typography.titleMedium,
                color = White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(daily.time) { dateStr ->
                    val index = daily.time.indexOf(dateStr)
                    val day = try {
                        Instant.parse(dateStr)
                            .atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("EEE"))
                    } catch (e: Exception) {
                        try {
                            LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE)
                                .atStartOfDay(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("EEE"))
                        } catch (ex: Exception) {
                            dateStr
                        }
                    }
                    DailyForecastCard(
                        day = day,
                        sunrise = daily.sunrise[index].split("T")[1],
                        sunset = daily.sunset[index].split("T")[1],
                        radiation = daily.solarRadiation[index],
                        precipitation = daily.precipitation[index]
                    )
                }
            }
        }
    }
}

