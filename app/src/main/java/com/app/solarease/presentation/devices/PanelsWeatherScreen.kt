package com.app.solarease.presentation.devices

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
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
import compose.icons.tablericons.Sunset
import compose.icons.tablericons.Sunshine
import compose.icons.tablericons.TemperatureCelsius

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
        SunTimingCard()
        CurrentConditionsCard()
        SolarIntensityCard()
        WeatherForecastCard()
    }
}

@Composable
private fun CurrentConditionsCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarYellow.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = TablerIcons.Sunshine,
                    contentDescription = "Current Conditions",
                    tint = SolarYellow,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Current Solar Conditions",
                    style = Typography.bodyMedium.copy(color = SolarYellow),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            SunIntensityBar(intensity = 0.85f, label = "Sun Intensity")
            SunIntensityBar(intensity = 0.3f, label = "Cloud Cover", color = SolarBlue)
        }
        }
    }
}

@Composable
private fun SunIntensityBar(
    intensity: Float,
    label: String,
    modifier: Modifier = Modifier,
    color: Color = SolarYellow
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Canvas(modifier = Modifier.size(40.dp, 100.dp)) {
            val barWidth = size.width * 0.6f
            val cornerRadius = 4.dp.toPx()
            
            drawRoundRect(
                color = White.copy(alpha = 0.1f),
                topLeft = Offset((size.width - barWidth)/2, 0f),
                size = Size(barWidth, size.height),
                cornerRadius = CornerRadius(cornerRadius)
            )

            val fillHeight = size.height * intensity
            drawRoundRect(
                color = color,
                topLeft = Offset((size.width - barWidth)/2, size.height - fillHeight),
                size = Size(barWidth, fillHeight),
                cornerRadius = CornerRadius(cornerRadius)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("${(intensity * 100).toInt()}%", color = White, style = Typography.labelMedium)
        Text(label, color = White.copy(alpha = 0.8f), fontSize = 12.sp)
    }
}

@Composable
private fun SolarIntensityCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarYellow.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = TablerIcons.ChartLine,
                    contentDescription = "Hourly Intensity",
                    tint = SolarYellow,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Hourly Solar Intensity",
                    style = Typography.bodyMedium.copy(color = SolarYellow),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            listOf(0.2f to "6AM", 0.4f to "9AM", 0.8f to "12PM", 0.9f to "3PM", 0.7f to "6PM", 0.3f to "9PM")
                .forEach { (value, time) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(time, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { value },
                            modifier = Modifier
                                .height(80.dp)
                                .width(12.dp),
                            color = SolarYellow,
                            trackColor = White.copy(alpha = 0.1f),
                        )
                    }
                }
        }
        }
    }
}

@Composable
private fun SunTimingCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SunTimingItem(TablerIcons.Sun, "Sunrise", "6:12 AM")
            SunTimingItem(TablerIcons.Sunset, "Sunset", "6:45 PM")
            SunTimingItem(TablerIcons.Bolt, "Peak Hours", "11AM-2PM")
        }
    }
}

@Composable
private fun SunTimingItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = SolarYellow,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, color = White, style = Typography.labelMedium)
        Text(label, color = White.copy(alpha = 0.8f), fontSize = 12.sp)
    }
}

@Composable
private fun WeatherForecastCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarYellow.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = TablerIcons.Cloud,
                    contentDescription = "Forecast",
                    tint = SolarYellow,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "3-Day Forecast",
                    style = Typography.bodyMedium.copy(color = SolarYellow),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ForecastDay("Tue", TablerIcons.Sun, "9h Sun", "5% Rain")
                ForecastDay("Wed", TablerIcons.Cloud, "6h Sun", "25% Rain")
                ForecastDay("Thu", TablerIcons.CloudRain, "2h Sun", "65% Rain")
            }
        }
    }
}

@Composable
private fun ForecastDay(day: String, icon: ImageVector, sunHours: String, rainChance: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Text(day, color = White.copy(alpha = 0.8f), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = icon,
            contentDescription = day,
            tint = SolarYellow,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(sunHours, color = White, style = Typography.labelMedium)
        Text(rainChance, color = SolarBlue, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PanelsWeatherScreenPreview() {
    SolarEaseTheme {
        PanelsWeatherScreen()
    }
}
