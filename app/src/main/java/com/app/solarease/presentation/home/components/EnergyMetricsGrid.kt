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
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.common.Resource
import com.app.solarease.presentation.common.components.InfoCard
import com.app.solarease.presentation.common.components.ProgressInfoCard
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarGreen
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.home.HomeViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.BatteryCharging
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Home
import compose.icons.tablericons.ZoomMoney

@Composable
fun EnergyMetricsGrid(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val energyMetrics = viewModel.energyMetrics.value

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Energy Overview",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        when (energyMetrics) {
            is Resource.Success -> {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProgressInfoCard(
                        title = "Battery Level",
                        value = energyMetrics.data.batteryLevel,
                        progress = energyMetrics.data.batteryProgress,
                        icon = TablerIcons.BatteryCharging,
                        color = SolarOrange,
                        secondaryValue = energyMetrics.data.batteryRemaining,
                        modifier = Modifier.weight(1f)
                    )
                    ProgressInfoCard(
                        title = "Home Usage",
                        value = energyMetrics.data.homeUsage,
                        progress = energyMetrics.data.homeUsageProgress,
                        icon = TablerIcons.Home,
                        color = SolarBlue,
                        secondaryValue = "Now",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoCard(
                        title = "Cost Savings",
                        value = energyMetrics.data.costSavings,
                        icon = TablerIcons.ZoomMoney,
                        color = SolarGreen,
                        secondaryValue = energyMetrics.data.costSavingsSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "CO2 Reduced",
                        value = energyMetrics.data.co2Reduced,
                        icon = TablerIcons.Bolt,
                        color = MaterialTheme.colorScheme.primary,
                        secondaryValue = energyMetrics.data.co2ReducedSecondary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            is Resource.Error -> {
                Text(
                    text = energyMetrics.message,
                    color = ErrorRed,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is Resource.Loading, null -> {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProgressInfoCard(
                        title = "Battery Level",
                        value = "--%",
                        progress = 0f,
                        icon = TablerIcons.BatteryCharging,
                        color = SolarOrange,
                        secondaryValue = "--",
                        modifier = Modifier.weight(1f)
                    )
                    ProgressInfoCard(
                        title = "Home Usage",
                        value = "-- kW",
                        progress = 0f,
                        icon = TablerIcons.Home,
                        color = SolarBlue,
                        secondaryValue = "--",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoCard(
                        title = "Cost Savings",
                        value = "-- Ksh",
                        icon = TablerIcons.ZoomMoney,
                        color = SolarGreen,
                        secondaryValue = "--",
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "CO2 Reduced",
                        value = "-- kg",
                        icon = TablerIcons.Bolt,
                        color = MaterialTheme.colorScheme.primary,
                        secondaryValue = "--",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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
