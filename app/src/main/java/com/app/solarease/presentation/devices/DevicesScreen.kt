package com.app.solarease.presentation.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.model.DeviceStatus
import com.app.solarease.domain.model.DeviceType
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.devices.components.DeviceCard

@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier,
    devices: List<Device> = defaultDevices,
    onDeviceClick: (Device) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            text = "Devices",
            style = Typography.headlineLarge,
            color = White,
            modifier = modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(devices) { device ->
                DeviceCard(
                    device = device,
                    onClick = { onDeviceClick(device) }
                )
            }
        }
    }
}

val defaultDevices = listOf(
    Device(
        id = "1",
        name = "Solar Array",
        type = DeviceType.SOLAR_PANEL,
        status = DeviceStatus.ONLINE,
        powerOutput = 4.8
    ),
    Device(
        id = "2",
        name = "Hybrid Inverter",
        type = DeviceType.INVERTER,
        status = DeviceStatus.ONLINE,
        efficiency = 97.5
    ),
    Device(
        id = "3",
        name = "Lithium Battery",
        type = DeviceType.BATTERY,
        status = DeviceStatus.ONLINE,
        capacity = 78
    )
)

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview() {
    SolarEaseTheme {
        DevicesScreen(devices = defaultDevices)
    }
}
