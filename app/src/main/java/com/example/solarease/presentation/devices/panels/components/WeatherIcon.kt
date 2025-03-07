package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun

@Composable
fun WeatherIcon(precipitation: Float) {
    val (icon, tint) = when {
        precipitation > 5 -> Pair(TablerIcons.CloudRain, SolarBlue)
        else -> Pair(TablerIcons.Sun, Yellow)
    }

    Icon(
        imageVector = icon,
        contentDescription = "Weather condition",
        tint = tint,
        modifier = Modifier.size(32.dp))
}
