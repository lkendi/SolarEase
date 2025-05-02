package com.app.solarease.presentation.devices

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.solarease.presentation.common.theme.SolarEaseTheme


@Composable
fun InverterScreen(
) {
    Text("Inverter Screen")
}

@Preview
@Composable
fun InverterScreenPreview() {
    SolarEaseTheme {
        InverterScreen()
    }
}
