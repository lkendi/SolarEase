package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.EnergyData
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.SolarBlue


@Composable
fun MetricsCards(energyData: EnergyData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EnergyMetricCard(
            title = "Generation",
            currentValue = energyData.generation.current,
            previousValue = energyData.generation.previous,
            unit = energyData.generation.unit,
            color = SolarBlue,
            modifier = Modifier.weight(1f)
        )
        EnergyMetricCard(
            title = "Consumption",
            currentValue = energyData.consumption.current,
            previousValue = energyData.consumption.previous,
            unit = energyData.consumption.unit,
            color = EnergyOrange,
            modifier = Modifier.weight(1f)
        )
    }
}
