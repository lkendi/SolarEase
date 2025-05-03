package com.app.solarease.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import com.app.solarease.presentation.devices.DeviceViewModel

@Composable
fun SystemDetailsScreen(
    viewModel: DeviceViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Panels", "Inverter", "Battery")

    val devicesState by viewModel.devicesState.collectAsState(Resource.Loading())

    LaunchedEffect(Unit) {
        viewModel.fetchDevices()
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "System Details",
            style = Typography.headlineMedium,
            color = White
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            tabs.forEachIndexed { index, title ->
                SegmentedButton(
                    onClick = { selectedTab = index },
                    selected = selectedTab == index,
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = tabs.size),
                    label = {
                        Text(
                            text = title,
                            color = if (selectedTab == index) SolarYellow else White
                        )
                    },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SolarYellow.copy(alpha = 0.1f),
                        inactiveContainerColor = Color.Transparent,
                        activeBorderColor = SolarYellow,
                        activeContentColor = SolarYellow
                    )
                )
            }
        }

        when (devicesState) {
            is Resource.Loading -> {
                Text(
                    text = "Loading devices...",
                    style = Typography.bodyMedium,
                    color = White
                )
            }
            is Resource.Success -> {
                val devices = (devicesState as Resource.Success).data
                val panels = devices.filterIsInstance<Device.Panel>()
                val inverter = devices.filterIsInstance<Device.Inverter>().firstOrNull()
                val battery = devices.filterIsInstance<Device.Battery>().firstOrNull()

                when (selectedTab) {
                    0 -> PanelDetails(panels)
                    1 -> InverterDetails(inverter)
                    2 -> BatteryDetails(battery)
                }
            }
            is Resource.Error -> {
                Text(
                    text = "Error: ${(devicesState as Resource.Error).message}",
                    style = Typography.bodyMedium,
                    color = White
                )
            }
        }
    }
}

@Composable
private fun PanelDetails(panels: List<Device.Panel>) {
    if (panels.isEmpty()) {
        Text(
            text = "No panels added yet.",
            style = Typography.bodyMedium,
            color = White
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DetailItem("Number of Panels", panels.size.toString())
            panels.forEachIndexed { index, panel ->
                DetailItem("Panel ${index + 1} Model", panel.model)
                DetailItem("Capacity", "${panel.capacity} W")
                DetailItem("Efficiency", "${(panel.efficiency * 100).toInt()}%")
                DetailItem("Tilt Angle", "${panel.tilt}°")
                panel.status?.let { DetailItem("Status", it) }
            }
        }
    }
}

@Composable
private fun InverterDetails(inverter: Device.Inverter?) {
    if (inverter == null) {
        Text(
            text = "No inverter added yet.",
            style = Typography.bodyMedium,
            color = White
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DetailItem("Inverter Model", inverter.model)
            DetailItem("Manufacturer", inverter.manufacturer)
            DetailItem("Capacity", "${inverter.capacity} W")
            DetailItem("Serial Number", inverter.serialNumber)
            DetailItem("Status", inverter.status)
        }
    }
}

@Composable
private fun BatteryDetails(battery: Device.Battery?) {
    if (battery == null) {
        Text(
            text = "No battery added yet.",
            style = Typography.bodyMedium,
            color = White
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DetailItem("Battery Model", battery.model)
            DetailItem("Manufacturer", battery.manufacturer)
            DetailItem("Capacity", "${battery.capacity} Ah")
            DetailItem("Voltage", "${battery.voltage} V")
            battery.status?.let { DetailItem("Status", it) }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = Typography.bodyMedium, color = White)
        Text(text = value, style = Typography.bodySmall, color = White)
    }
}

@Preview(showBackground = true)
@Composable
fun SystemDetailsScreenPreview() {
    SolarEaseTheme {
        SystemDetailsScreen()
    }
}
