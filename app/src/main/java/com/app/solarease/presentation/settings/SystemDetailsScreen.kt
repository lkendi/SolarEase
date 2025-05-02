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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White

@Composable
fun SystemDetailsScreen(
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Panels", "Inverter", "Battery")

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
                            title,
                            color = if (selectedTab == index) SolarYellow else White
                        )
                    },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SolarYellow.copy(alpha=0.1f),
                        inactiveContainerColor = Color.Transparent,
                        activeBorderColor = SolarYellow,
                        activeContentColor = SolarYellow,
                    )
                )
            }
        }

        when (selectedTab) {
            0 -> PanelDetails()
            1 -> InverterDetails()
            2 -> BatteryDetails()
        }
    }
}

@Composable
private fun PanelDetails() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DetailItem("Number of Panels", "24")
        DetailItem("Panel Model", "AX-500W")
        DetailItem("Total Capacity", "12 kW")
        DetailItem("Orientation", "South-East")
        DetailItem("Tilt Angle", "30°")
    }
}

@Composable
private fun InverterDetails() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DetailItem("Inverter Model", "SolarX 5000")
        DetailItem("Capacity", "5 kW")
        DetailItem("Firmware Version", "v1.2.3")
        DetailItem("String Configuration", "2 × 12 panels")
    }
}

@Composable
private fun BatteryDetails() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DetailItem("Battery Capacity", "10 kWh")
        DetailItem("State of Charge", "78%")
        DetailItem("Battery Model", "PowerStore X")
        DetailItem("Health", "Good (95%)")
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
