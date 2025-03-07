package com.example.solarease.presentation.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.R
import com.example.solarease.domain.model.Device
import com.example.solarease.domain.model.DeviceStatus
import com.example.solarease.domain.model.DeviceType
import com.example.solarease.presentation.common.theme.EcoGreen
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow

@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier,
    devices: List<Device> = listOf(
        Device(
            id = "1",
            name = "Solar Panels",
            type = DeviceType.SOLAR_PANEL,
            status = DeviceStatus.ONLINE,
            powerOutput = 4.8
        ),
        Device(
            id = "2",
            name = "Main Inverter",
            type = DeviceType.INVERTER,
            status = DeviceStatus.WARNING
        ),
        Device(
            id = "3",
            name = "Battery",
            type = DeviceType.BATTERY,
            status = DeviceStatus.ONLINE,
            capacity = 78
        )
    ),
    onDeviceClick: (Device) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            text = "Devices",
            style = Typography.headlineMedium,
            color = White,
            modifier = modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            devices.forEach { device ->
                DeviceCard(
                    device = device,
                    onClick = { onDeviceClick(device) }
                )
            }
        }
    }
}


@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit
) {
    val (statusColor, statusText, helperText) = when (device.status) {
        DeviceStatus.ONLINE -> Triple(EcoGreen, "Connected", "Working properly")
        DeviceStatus.OFFLINE -> Triple(ErrorRed, "Disconnected", "Check connection")
        DeviceStatus.WARNING -> Triple(Yellow, "Needs Attention", "Action required")
    }

    val (typeIcon, typeDescription) = when (device.type) {
        DeviceType.SOLAR_PANEL -> Pair(R.drawable.panels, "Energy Production")
        DeviceType.INVERTER -> Pair(R.drawable.inverter, "Power Converter")
        DeviceType.BATTERY -> Pair(R.drawable.battery, "Energy Storage")
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Yellow.copy(alpha = 0.05f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Text(
                        text = statusText,
                        style = Typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = typeDescription,
                    style = Typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = typeIcon),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = device.name,
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    when (device.type) {
                        DeviceType.SOLAR_PANEL -> {
                            Text(
                                text = "Producing ${device.powerOutput} kW",
                                style = Typography.bodySmall,
                                color = EcoGreen
                            )
                            Text(
                                text = "At its peak production",
                                style = Typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        DeviceType.BATTERY -> {
                            Column {
                                Text(
                                    text = "${device.capacity}% - Charging",
                                    style = Typography.bodySmall,
                                    color = Yellow
                                )
                                LinearProgressIndicator(
                                    progress = { device.capacity?.toFloat()?.div(100) ?: 0f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = Yellow,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                        DeviceType.INVERTER -> {
                            Text(
                                text = "Converting power",
                                style = Typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = helperText,
                                style = Typography.labelSmall,
                                color = statusColor
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview() {
    SolarEaseTheme {
        DevicesScreen()
    }
}
