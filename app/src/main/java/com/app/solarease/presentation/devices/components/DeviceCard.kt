package com.app.solarease.presentation.devices.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.app.solarease.R
import com.app.solarease.domain.model.Device
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.SuccessGreen
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit
) {
    val (statusColor, statusText, helperText) = when (device.status) {
        "online" -> Triple(SuccessGreen, "Connected", "Working properly")
        "offline" -> Triple(ErrorRed, "Disconnected", "Check connection")
        else -> Triple(SolarYellow, "Needs Attention", "Action required")
    }

    val typeIcon = when (device.deviceType) {
        "panel" -> R.drawable.panels
        "inverter" -> R.drawable.inverter
        "battery" -> R.drawable.battery
        else -> R.drawable.inverter
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SolarYellow.copy(alpha = 0.2f),
            contentColor = White
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = typeIcon),
                    contentDescription = "${device.deviceType} icon",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = device.deviceType.replaceFirstChar { it.uppercase() },
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
                    when (device) {
                        is Device.Panel -> {
                            Text(
                                text = "Capacity: 7V",
                                style = Typography.bodyMedium,
                                color = SuccessGreen
                            )
                            Text(
                                text = "Efficiency: ${(device.efficiency * 100).toInt()}%",
                                style = Typography.labelSmall,
                                color = White
                            )
                        }
                        is Device.Battery -> {
                            Column {
                                val simulatedCharge = 48f
                                Text(
                                    text = "$simulatedCharge% Charged",
                                    style = Typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                LinearProgressIndicator(
                                    progress = { simulatedCharge / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                                Text(
                                    text = "Capacity: ${device.capacity} Ah",
                                    style = Typography.labelSmall,
                                    color = White
                                )
                            }
                        }
                        is Device.Inverter -> {
                            Text(
                                text = "Capacity: ${device.capacity}",
                                style = Typography.bodySmall,
                                color = White
                            )
                            Text(
                                text = "Type: ${device.inverterType}",
                                style = Typography.labelSmall,
                                color = White
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
fun DeviceCardPreview() {
    val device = Device.Inverter(
        id = "inverter_001",
        model = "Sample Inverter 1000",
        manufacturer = "SolarCo",
        status = "online",
        serialNumber = "INV12345",
        capacity = "1000.0",
        acInputCurrent = null,
        acInputFrequency = null,
        acInputVoltage = null,
        inverterType = "Off-Grid",
        dcInputCurrent = null,
        dcInputFrequency = null,
        dcInputVoltage = null,
        inverterModeCurrent = null,
        inverterModeFrequency = null,
        inverterModeVoltage = null,
        chargingVoltage = null,
        peakPower = null,
        solarChargingMode = null,
        solarInputMaxCurrent = null,
        solarInputMaxVoltage = null,
        solarInputVoltageRange = null
    )
    SolarEaseTheme {
        DeviceCard(
            device = device,
            onClick = { /* Nothing */ }
        )
    }
}
