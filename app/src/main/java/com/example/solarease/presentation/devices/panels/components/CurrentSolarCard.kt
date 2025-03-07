package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.weather.CurrentWeather
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun

@Composable
fun CurrentSolarCard(current: CurrentWeather) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkGrey.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Current Solar Conditions",
                style = Typography.titleMedium,
                color = Yellow,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SolarMetric(
                    label = "Irradiance",
                    value = "${current.solarRadiation.toInt()}W/m²",
                    icon = TablerIcons.Sun,
                    modifier = Modifier.weight(1f)
                )

                SolarMetric(
                    label = "Cloud Cover",
                    value = "${current.cloudCover}%",
                    icon = TablerIcons.Cloud,
                    modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SolarMetric(
                    label = "Rainfall",
                    value = "${current.precipitation}mm",
                    icon = TablerIcons.CloudRain,
                    modifier = Modifier.weight(1f))

                SolarMetric(
                    label = "Sunshine",
                    value = "${current.sunshineDuration.toInt()}h",
                    icon = TablerIcons.Sun,
                    modifier = Modifier.weight(1f))
            }
        }
    }
}
