package com.example.solarease.presentation.devices.panels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.solarease.domain.model.weather.CurrentWeather
import com.example.solarease.domain.model.weather.DailyData
import com.example.solarease.domain.model.weather.HourlyData
import com.example.solarease.domain.model.weather.WeatherResponse
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import com.example.solarease.presentation.devices.panels.components.CurrentSolarCard
import com.example.solarease.presentation.devices.panels.components.DailySolarForecast
import com.example.solarease.presentation.devices.panels.components.HourlyIrradianceChart
import com.example.solarease.presentation.devices.panels.components.PanelMetricsGrid
import com.example.solarease.presentation.devices.panels.components.WeatherError
import com.example.solarease.presentation.devices.panels.components.WeatherLoading
import com.example.solarease.presentation.devices.panels.components.WeatherRecommendations
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.time.Instant

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PanelsWeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    val locationPermissionState = rememberPermissionState(
//        permission = Manifest.permission.ACCESS_FINE_LOCATION
//    )
//
//    LaunchedEffect(key1 = locationPermissionState.status.isGranted) {
//        if (!locationPermissionState.status.isGranted) {
//            locationPermissionState.launchPermissionRequest()
//        } else {
//            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                location?.let {
//                    viewModel.loadWeatherData(it.latitude, it.longitude)
//                }
//            }
//        }
//    }

    val nairobiLatitude = -1.286389
    val nairobiLongitude = 36.817223

    LaunchedEffect(Unit) {
        viewModel.loadWeatherData(nairobiLatitude, nairobiLongitude)
    }

    val weatherState by viewModel.weatherState.collectAsState()
    PanelsWeatherContent(weatherState = weatherState)
}

@Composable
fun PanelsWeatherContent(weatherState: WeatherState) {
    var selectedSection by remember { mutableIntStateOf(1) }
    val sections = listOf("Panels", "Weather")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Panels & Weather",
                    style = MaterialTheme.typography.headlineMedium,
                    color = White
                )
            }

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                sections.forEachIndexed { index, label ->
                    val segmentShape = when (index) {
                        0 -> RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        )
                        sections.lastIndex -> RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = 12.dp,
                            bottomEnd = 12.dp
                        )
                        else -> RectangleShape
                    }
                    SegmentedButton(
                        selected = selectedSection == index,
                        onClick = { selectedSection = index },
                        shape = segmentShape,
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = Yellow.copy(alpha = 0.08f),
                            inactiveContainerColor = Color.Transparent,
                            activeContentColor = Yellow,
                            inactiveContentColor = White.copy(alpha = 0.8f),
                            activeBorderColor = Yellow.copy(alpha = 0.5f),
                            inactiveBorderColor = White.copy(alpha = 0.4f)
                        )
                    ) {
                        Text(
                            text = label,
                            color = if (selectedSection == index) Yellow else White.copy(alpha = 0.8f),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            when (selectedSection) {
                0 -> PanelMetricsGrid()
                1 -> WeatherContent(state = weatherState)
            }

            Spacer(modifier = Modifier.height(24.dp))
            WeatherRecommendations(state = weatherState)
        }
    }
}

@Composable
private fun WeatherContent(state: WeatherState) {
    when (state) {
        is WeatherState.Loading -> WeatherLoading()
        is WeatherState.Success -> SolarWeatherData(state.data)
        is WeatherState.Error -> WeatherError(state.message)
    }
}

@Composable
private fun SolarWeatherData(data: WeatherResponse) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CurrentSolarCard(data.current)
        HourlyIrradianceChart(data.hourly)
        DailySolarForecast(data.daily)
    }
}

private val dummyWeatherResponse = WeatherResponse(
    latitude = 52.520008,
    longitude = 13.404954,
    current = CurrentWeather(
        temperature = 25f,
        solarRadiation = 900f,
        cloudCover = 20,
        sunshineDuration = 20f,
        precipitation = 0f
    ),
    hourly = HourlyData(
        time = List(12) { Instant.now().toString() },
        cloudCover = List(12) { 20 },
        solarRadiation = List(12) { 900f }
    ),
    daily = DailyData(
        time = List(7) { Instant.now().toString() },
        sunrise = List(7) { "2025-03-06T06:00:00Z" },
        sunset = List(7) { "2025-03-06T18:00:00Z" },
        solarRadiation = List(7) { 5000f },
        precipitation = List(7) { 0f },
    )
)

@Preview
@Composable
fun PanelsWeatherScreenPreview() {
    SolarEaseTheme {
        PanelsWeatherContent(
            weatherState = WeatherState.Success(dummyWeatherResponse)
        )
    }
}
