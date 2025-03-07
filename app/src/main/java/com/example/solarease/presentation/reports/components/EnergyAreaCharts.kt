package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.EnergyDataPoint
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White

@Composable
fun EnergyAreaCharts(
    dataPoints: List<EnergyDataPoint>,
    timeInterval: TimeInterval
) {
    val maxValue = dataPoints.maxOf { maxOf(it.generation, it.consumption) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Charts",
            style = Typography.titleLarge,
            color = White
        )
        EnergyAreaChart(
            title = "Energy Generation",
            dataPoints = dataPoints.map { it.generation },
            maxValue = maxValue,
            timeInterval = timeInterval,
            color = SolarBlue
        )

        EnergyAreaChart(
            title = "Energy Consumption",
            dataPoints = dataPoints.map { it.consumption },
            maxValue = maxValue,
            timeInterval = timeInterval,
            color = EnergyOrange
        )
    }
}
