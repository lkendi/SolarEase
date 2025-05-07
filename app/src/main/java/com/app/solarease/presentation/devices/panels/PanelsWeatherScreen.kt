package com.app.solarease.presentation.devices.panels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.components.InfoCard
import com.app.solarease.presentation.common.components.ProgressInfoCard
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarGreen
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import com.app.solarease.presentation.devices.components.CurrentConditionsCard
import com.app.solarease.presentation.devices.components.SolarIntensityCard
import com.app.solarease.presentation.devices.components.SunTimingCard
import com.app.solarease.presentation.devices.components.WeatherForecastCard
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.ChartLine
import compose.icons.tablericons.Leaf
import compose.icons.tablericons.TemperatureCelsius

@Composable
fun PanelsWeatherScreen(
    navController: NavController? = null,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Panels", "Weather")
    val state by viewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(lat = -1.2921, lon = 36.8219)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Panels & Weather", style = Typography.headlineMedium, color = White)
        Spacer(Modifier.height(16.dp))
        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { i, title ->
                SegmentedButton(
                    selected = selectedTab == i,
                    onClick = { selectedTab = i },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SolarYellow.copy(alpha = 0.1f),
                        inactiveContainerColor = Color.Transparent,
                        activeBorderColor = SolarYellow,
                        activeContentColor = SolarYellow
                    ),
                    shape = SegmentedButtonDefaults.itemShape(i, tabs.size)
                ) { Text(title) }
            }
        }
        Spacer(Modifier.height(16.dp))
        if (selectedTab == 0) {
            PanelsView()
        } else {
            when (state) {
                is Resource.Loading -> Text("Loading...", color = White)
                is Resource.Error   -> Text("Error: ${(state as Resource.Error).message}", color = White)
                is Resource.Success -> WeatherView((state as Resource.Success<Weather>).data)
            }
        }
    }
}

@Composable
private fun PanelsView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                title = "Panel Temp",
                value = "42°C",
                modifier = Modifier.weight(1f),
                color = SolarYellow,
                secondaryValue = "Normal"
            )
            InfoCard(
                icon = TablerIcons.Leaf,
                title = "Ambient Temp",
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
            Column(Modifier.padding(16.dp)) {
                Text("Recommendations", style = Typography.bodyMedium, color = SolarYellow)
                Spacer(Modifier.height(8.dp))
                Text("• Clean panels in the morning", color = White, style = Typography.bodySmall)
                Text("• Check east array connections", color = White, style = Typography.bodySmall)
                Text("• Optimal tilt maintained", color = White, style = Typography.bodySmall)
            }
        }
    }
}

@Composable
private fun WeatherView(weather: Weather) {
    val daylightHours = weather.hourly.filter { it.radiation > 0 }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SunTimingCard(weather)
        CurrentConditionsCard(weather)

        if (daylightHours.isNotEmpty()) {
            SolarIntensityCard(weather.copy(hourly = daylightHours))
        } else {
            Text("No solar radiation data available", color = White)
        }

        WeatherForecastCard(weather)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPanelsWeatherScreen() {
    SolarEaseTheme { PanelsWeatherScreen() }
}
