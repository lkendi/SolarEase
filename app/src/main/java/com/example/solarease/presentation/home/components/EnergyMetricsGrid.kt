package com.example.solarease.presentation.home.components

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
import compose.icons.tablericons.BatteryCharging
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Plant
import compose.icons.tablericons.Sun

@Composable
fun EnergyMetricsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ProgressInfoCard(
                title = "Solar Production",
                value = "264 kWh",
                progress = 0.72f,
                icon = TablerIcons.Sun,
                color = EnergyOrange,
                modifier = Modifier.weight(1f)
            )

            InfoCard(
                title = "Daily Usage",
                value = "26 kWh",
                secondaryValue = "+2%",
                icon = TablerIcons.Bolt,
                color = SolarBlue,
                modifier = Modifier.weight(1f))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ProgressInfoCard(
                title = "Battery Level",
                value = "60%",
                progress = 0.52f,
                icon = TablerIcons.BatteryCharging,
                color = SolarGreen,
                modifier = Modifier.weight(1f)
            )

            InfoCard(
                title = "CO2 Reduced",
                value = "22 kg",
                secondaryValue = "= 12 trees",
                icon = TablerIcons.Plant,
                color = Yellow,
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Preview
@Composable
fun EnergyMetricsGridPreview() {
    SolarEaseTheme {
        EnergyMetricsGrid()
    }
}
