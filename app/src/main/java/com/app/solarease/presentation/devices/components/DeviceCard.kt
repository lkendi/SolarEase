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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.R
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.model.DeviceStatus
import com.app.solarease.domain.model.DeviceType
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.SuccessGreen
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.WarningAmber

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit
) {
    val (statusColor, statusText, helperText) = when (device.status) {
        DeviceStatus.ONLINE -> Triple(SuccessGreen, "Connected", "Working properly")
        DeviceStatus.OFFLINE -> Triple(ErrorRed, "Disconnected", "Check connection")
        DeviceStatus.WARNING -> Triple(WarningAmber, "Needs Attention", "Action required")
    }

    val typeIcon = when (device.type) {
        DeviceType.SOLAR_PANEL -> R.drawable.panels
        DeviceType.INVERTER -> R.drawable.inverter
        DeviceType.BATTERY -> R.drawable.battery
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SolarYellow.copy(alpha = 0.2f),
            contentColor = Color.White
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
                                style = Typography.bodyMedium,
                                color = SuccessGreen
                            )
                            Text(
                                text = "Enough for 2 days",
                                style = Typography.labelSmall,
                                color = Color.White
                            )
                        }
                        DeviceType.BATTERY -> {
                            Column {
                                Text(
                                    text = "${device.capacity}% Charged",
                                    style = Typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                LinearProgressIndicator(
                                    progress = { device.capacity?.toFloat()?.div(100) ?: 0f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                        DeviceType.INVERTER -> {
                            Text(
                                text = "Converting power",
                                style = Typography.bodySmall,
                                color = SuccessGreen
                            )
                            Text(
                                text = helperText,
                                style = Typography.labelSmall,
                                color = Color.White
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
    val device = Device(
        name = "Solar Panel",
        type = DeviceType.SOLAR_PANEL,
        status = DeviceStatus.ONLINE,
        powerOutput = 5.4,
        capacity = null,
        id = 123.toString()
    )
    SolarEaseTheme {
        DeviceCard(
            device = device,
            onClick = { /* Nothing */ }
        )
    }
}