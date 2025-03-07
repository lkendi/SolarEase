package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.components.InfoCard
import com.example.solarease.presentation.common.components.ProgressInfoCard
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.SolarGreen
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.ChartDots
import compose.icons.tablericons.Temperature
import compose.icons.tablericons.TemperatureCelsius


@Composable
fun PanelMetricsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ProgressInfoCard(
                title = "Total Output",
                value = "4.2 kW",
                progress = 0.72f,
                icon = TablerIcons.Bolt,
                color = SolarBlue,
                modifier = Modifier.weight(1f)
            )
            ProgressInfoCard(
                title = "Panel Efficiency",
                value = "92%",
                progress = 0.88f,
                icon = TablerIcons.ChartDots,
                color = SolarGreen,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoCard(
                title = "Panel Temp.",
                value = "45°C",
                secondaryValue = "+2%",
                icon = TablerIcons.Temperature,
                color = EnergyOrange,
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = "Ambient Temp.",
                value = "42°C",
                secondaryValue = "+3°C",
                icon = TablerIcons.TemperatureCelsius,
                color = Yellow,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PanelMetricsGridPreview() {
    SolarEaseTheme {
        PanelMetricsGrid()
    }
}
