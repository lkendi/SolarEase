package com.example.solarease.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SkyBlue
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Sun

@Composable
fun WeatherCard() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        StatusCard(
            modifier = Modifier.weight(1f),
            icon = TablerIcons.Sun,
            title = "Weather",
            value = "24°C",
            subtitle = "Sunny",
            gradient = Brush.horizontalGradient(listOf(SolarBlue, SkyBlue))
        )

    }
}

@Preview
@Composable
fun StatusGridPreview() {
    SolarEaseTheme {
        WeatherCard()
    }
}
