package com.app.solarease.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.NavController
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import com.app.solarease.presentation.viewmodel.DeviceViewModel
import java.util.Locale

@Composable
fun SystemDetailsScreen(
    navController: NavController? = null,
    viewModel: DeviceViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Panels", "Inverter", "Battery")
    val devicesState by viewModel.devicesState.collectAsState(initial = Resource.Loading())

    LaunchedEffect(Unit) {
        viewModel.fetchDevices()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(
            text = "System Details",
            style = Typography.headlineMedium,
            color = White
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
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

        when (val state = devicesState) {
            is Resource.Loading -> Text(
                text = "Loading devices...",
                style = Typography.bodyMedium,
                color = White
            )
            is Resource.Error -> Text(
                text = "Error: ${state.message}",
                style = Typography.bodyMedium,
                color = White
            )
            is Resource.Success -> {
                val devices = state.data
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        when (selectedTab) {
                            0 -> {
                                val panels = devices.filterIsInstance<Device.Panel>()
                                if (panels.isEmpty()) {
                                    Text(text = "No panels added yet.",
                                        style = Typography.bodyMedium,
                                        color = White
                                    )
                                } else {
                                    panels.forEach { panel -> DynamicDetailItem(panel) }
                                }
                            }
                            1 -> {
                                val inverter = devices.filterIsInstance<Device.Inverter>().firstOrNull()
                                if (inverter == null) {
                                    Text(
                                        text = "No inverter added yet.",
                                        style = Typography.bodyMedium,
                                        color = White
                                    )
                                } else {
                                    InverterDetail(inverter)
                                }
                            }
                            2 -> {
                                val battery = devices.filterIsInstance<Device.Battery>().firstOrNull()
                                if (battery == null) {
                                    Text(text = "No battery added yet.",
                                        style = Typography.bodyMedium,
                                        color = White
                                    )
                                } else {
                                    DynamicDetailItem(battery)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InverterDetail(inv: Device.Inverter) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionTitle("General")
        DetailItemRow("Model", inv.model)
        DetailItemRow("Manufacturer", inv.manufacturer)
        DetailItemRow("Status", inv.status)
        DetailItemRow("Serial Number", inv.serialNumber)
        DetailItemRow("Capacity", inv.capacity)

        SectionTitle("DC Input")
        DetailItemRow("DC Current", inv.dcInputCurrent ?: "—")
        DetailItemRow("DC Voltage", inv.dcInputVoltage ?: "—")
        DetailItemRow("DC Frequency", inv.dcInputFrequency ?: "—")

        SectionTitle("AC Input")
        DetailItemRow("AC Current", inv.acInputCurrent ?: "—")
        DetailItemRow("AC Voltage", inv.acInputVoltage ?: "—")
        DetailItemRow("AC Frequency", inv.acInputFrequency ?: "—")

        SectionTitle("Inverter Mode")
        DetailItemRow("Mode Current", inv.inverterModeCurrent ?: "—")
        DetailItemRow("Mode Voltage", inv.inverterModeVoltage ?: "—")
        DetailItemRow("Mode Frequency", inv.inverterModeFrequency ?: "—")

        SectionTitle("Solar Input")
        DetailItemRow("Solar Max Current", inv.solarInputMaxCurrent ?: "—")
        DetailItemRow("Solar Max Voltage", inv.solarInputMaxVoltage ?: "—")
        DetailItemRow("Charging Voltage", inv.chargingVoltage ?: "—")
        DetailItemRow("Peak Power", inv.peakPower ?: "—")
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = Typography.titleSmall,
        color = SolarYellow,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun DetailItemRow(label: String, value: String) {
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

@Composable
private fun DynamicDetailItem(device: Device) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val excluded = setOf("id", "deviceType")
        device::class.java.declaredFields
            .filter { it.name !in excluded }
            .onEach { it.isAccessible = true }
            .mapNotNull { field ->
                val value = field.get(device)?.toString() ?: return@mapNotNull null
                val label = field.name
                    .replace(Regex("([a-z])([A-Z]+)"), "$1 $2")
                    .replaceFirstChar { it.uppercase(Locale.ROOT) }
                label to value
            }
            .forEach { (label, value) -> DetailItemRow(label, value) }
    }
}

@Preview(showBackground = true)
@Composable
fun SystemDetailsScreenPreview() {
    SolarEaseTheme {
        SystemDetailsScreen()
    }
}
