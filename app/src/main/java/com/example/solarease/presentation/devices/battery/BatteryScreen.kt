package com.example.solarease.presentation.devices.battery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.EcoGreen
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import com.example.solarease.presentation.devices.battery.components.BatteryChargingIndicator
import com.example.solarease.presentation.home.components.StatusIndicatorCard
import compose.icons.TablerIcons
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Temperature
import compose.icons.tablericons.TemperatureCelsius

@Composable
fun BatteryScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Battery",
                    style = Typography.headlineMedium,
                    color = White
                )
                Text(
                    text = "Lead Acid - 12V",
                    color = Yellow,
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BatteryChargingIndicator(progress = 0.57f, size = 250.dp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusIndicatorCard(
                    icon = TablerIcons.Heart,
                    label = "Battery Health",
                    status = "Good",
                    statusColor = EcoGreen,
                    modifier = Modifier.weight(1f)
                )
                StatusIndicatorCard(
                    icon = TablerIcons.Temperature,
                    label = "Battery Temp",
                    status = "20°C",
                    statusColor = Yellow,
                    modifier = Modifier.weight(1f)
                )
                StatusIndicatorCard(
                    icon = TablerIcons.TemperatureCelsius,
                    label = "Ambient Temp",
                    status = "22°C",
                    statusColor = ErrorRed,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Recommendations",
                color = White,
                style = Typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BatteryScreenPreview() {
    SolarEaseTheme {
        BatteryScreen()
    }
}
