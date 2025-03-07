package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarGreen

@Composable
fun PerformanceLegend() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LegendItem(SolarGreen, "Optimal")
        LegendItem(EnergyOrange, "Warning")
        LegendItem(ErrorRed, "Critical")
    }
}