package com.app.solarease.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.components.InfoCard
import com.app.solarease.presentation.common.components.ProgressInfoCard
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarGreen
import com.app.solarease.presentation.common.theme.SolarOrange
import compose.icons.TablerIcons
import compose.icons.tablericons.BatteryCharging
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Home
import compose.icons.tablericons.ZoomMoney

@Composable
fun EnergyMetricsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Energy Overview",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ProgressInfoCard(
                title = "Battery Level",
                value = "82%",
                progress = 0.82f,
                icon = TablerIcons.BatteryCharging,
                color = SolarOrange,
                secondaryValue = "4h remaining",
                modifier = Modifier.weight(1f)
            )
            ProgressInfoCard(
                title = "Home Usage",
                value = "2.4 kW",
                progress = 0.35f,
                icon = TablerIcons.Home,
                color = SolarBlue,
                secondaryValue = "Now",
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoCard(
                title = "Cost Savings",
                value = "1,250 Ksh",
                icon = TablerIcons.ZoomMoney,
                color = SolarGreen,
                secondaryValue = "+12% more than last month",
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = "CO2 Reduced",
                value = "12 kg",
                icon = TablerIcons.Bolt,
                color = MaterialTheme.colorScheme.primary,
                secondaryValue = "Equivalent to 12 trees",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun EnergyMetricsGridPreview(){
    SolarEaseTheme {
        EnergyMetricsGrid()
    }
}
