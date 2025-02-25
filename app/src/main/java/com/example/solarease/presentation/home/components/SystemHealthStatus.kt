package com.example.solarease.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.SolarGreen
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.BatteryCharging
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Sun

@Composable
fun SystemHealthStatus() {
    Column {
        Text(
            text = "System Health",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SystemStatusIndicator(
                icon = TablerIcons.Sun,
                label = "Panels",
                status = "Optimal",
                statusColor = SolarGreen,
                modifier = Modifier.weight(1f)
            )
            SystemStatusIndicator(
                icon = TablerIcons.Bolt,
                label = "Inverter",
                status = "Warning",
                statusColor = Yellow,
                modifier = Modifier.weight(1f)
            )
            SystemStatusIndicator(
                icon = TablerIcons.BatteryCharging,
                label = "Battery",
                status = "Warning",
                statusColor = Yellow,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun SystemHealthStatusPreview() {
    SolarEaseTheme {
        SystemHealthStatus()
    }
}
