package com.app.solarease.presentation.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.solarease.presentation.common.components.InfoCard
import com.app.solarease.presentation.common.components.ProgressInfoCard
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarGreen
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.ChartLine
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Leaf
import compose.icons.tablericons.Sun
import compose.icons.tablericons.TemperatureCelsius
import compose.icons.tablericons.Wind

@Composable
fun PanelsWeatherScreen() {
    var selectedSegment by remember { mutableIntStateOf(0) }
    val segments = listOf("Panels", "Weather")

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Panels & Weather",
            style = Typography.headlineMedium,
            color = White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            segments.forEachIndexed { index, title ->
                SegmentedButton(
                    selected = selectedSegment == index,
                    onClick = { selectedSegment = index },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SolarYellow.copy(alpha=0.1f),
                        inactiveContainerColor = Color.Transparent,
                        activeBorderColor = SolarYellow,
                        activeContentColor = SolarYellow,
                    ),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = segments.size
                    )
                ) {
                    Text(text = title)
                }
            }
        }

        when (selectedSegment) {
            0 -> PanelsView()
            1 -> WeatherView()
        }
    }
}

@Composable
private fun PanelsView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProgressInfoCard(
                title = "Current Output",
                value = "4.2 kW",
                progress = 0.65f,
                secondaryValue = "6.5 kW max",
                modifier = Modifier.weight(1f),
                icon = TablerIcons.Bolt,
                color = SolarOrange
            )
            ProgressInfoCard(
                title = "Efficiency",
                value = "92%",
                progress = 0.92f,
                secondaryValue = "Peak performance",
                modifier = Modifier.weight(1f),
                icon = TablerIcons.ChartLine,
                color = SolarGreen
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoCard(
                icon = TablerIcons.TemperatureCelsius,
                title = "Panel Temperature",
                value = "42°C",
                modifier = Modifier.weight(1f),
                color = SolarYellow,
                secondaryValue = "Normal"
            )
            InfoCard(
                icon = TablerIcons.Leaf,
                title = "Ambient Temperature",
                value = "28°C",
                modifier = Modifier.weight(1f),
                color = SolarBlue,
                secondaryValue = "Normal"
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Recommendations",
                    style = Typography.bodyMedium,
                    color = SolarYellow,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text("• Clean panels in the morning", color = White, style = Typography.bodySmall)
                Text("• Check east array connections", color = White, style = Typography.bodySmall)
                Text("• Optimal tilt maintained", color = White, style = Typography.bodySmall)
            }
        }
    }
}

@Composable
private fun WeatherView() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = TablerIcons.Sun,
                    contentDescription = null,
                    tint = SolarYellow,
                    modifier = Modifier.size(40.dp)
                )
                Column {
                    Text("Current Conditions", color = SolarYellow, style = Typography.labelMedium)
                    Text("Sunny", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("UV Index: 8 (High)", color = White.copy(alpha = 0.8f))
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Hourly Solar Intensity",
                    style = Typography.titleMedium,
                    color = SolarYellow,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(0.2f, 0.4f, 0.8f, 0.9f, 0.7f, 0.3f).forEachIndexed { index, value ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${index + 6}H", color = White.copy(alpha = 0.6f), fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { value },
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(12.dp),
                                color = SolarYellow,
                                trackColor = Color.Gray.copy(alpha = 0.3f),
                            )
                        }
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ForecastItem(icon = TablerIcons.Cloud, title = "Cloud", value = "12%")
                ForecastItem(icon = TablerIcons.CloudRain, title = "Rain", value = "5%")
                ForecastItem(icon = TablerIcons.Wind, title = "Wind", value = "8 km/h")
            }
        }
    }
}


@Composable
private fun ForecastItem(icon: ImageVector, title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SolarYellow,
            modifier = Modifier.size(32.dp)
        )
        Text(title, color = White.copy(alpha = 0.8f))
        Text(value, color = White)
    }
}

@Preview(showBackground = true)
@Composable
fun PanelsWeatherScreenPreview() {
    SolarEaseTheme {
        PanelsWeatherScreen()
    }
}
