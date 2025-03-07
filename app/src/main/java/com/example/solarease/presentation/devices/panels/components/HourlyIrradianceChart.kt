package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.weather.HourlyData
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HourlyIrradianceChart(hourly: HourlyData) {
    val maxRadiation = hourly.solarRadiation.maxOrNull() ?: 1000f
    val barHeight = 120.dp

    Card(
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Hourly Solar Intensity",
                style = Typography.titleMedium,
                color = White
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val filteredData = hourly.time.mapIndexedNotNull { index, time ->
                    try {
                        val instant = Instant.parse(time)
                        val hour = instant.atZone(ZoneId.systemDefault()).hour
                        if (hour in 6..20) Pair(time, hourly.solarRadiation[index]) else null
                    } catch (e: Exception) {
                        try {
                            val instant = LocalDateTime.parse(time)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                            val hour = instant.atZone(ZoneId.systemDefault()).hour
                            if (hour in 6..20) Pair(time, hourly.solarRadiation[index]) else null
                        } catch (ex: Exception) {
                            null
                        }
                    }
                }

                filteredData.forEach { (time, radiationValue) ->
                    val instant = try {
                        Instant.parse(time)
                    } catch (e: Exception) {
                        LocalDateTime.parse(time)
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                    }
                    val formattedHour = instant.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("HH:mm"))

                    RadiationBar(
                        time = formattedHour,
                        value = radiationValue,
                        max = maxRadiation,
                        height = barHeight
                    )
                }
            }
        }
    }
}
