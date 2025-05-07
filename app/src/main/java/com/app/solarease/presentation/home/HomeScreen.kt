package com.app.solarease.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.common.Resource
import com.app.solarease.presentation.auth.AuthState
import com.app.solarease.presentation.auth.AuthViewModel
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.devices.panels.WeatherViewModel
import com.app.solarease.presentation.home.components.EnergyMetricsGrid
import com.app.solarease.presentation.home.components.GreetingSection
import com.app.solarease.presentation.home.components.SystemHealthStatus
import com.app.solarease.presentation.home.components.WeatherCard

@Composable
fun HomeScreen(
    notificationCount: Int = 0,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val weatherState by weatherViewModel.weatherState.collectAsState()

    val user = (authState as? AuthState.Authenticated)?.user

    LaunchedEffect(Unit) {
        weatherViewModel.fetchWeather(
            lat = -1.2921,
            lon = 36.8219
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GreetingSection(
            userName = user?.displayName ?: "User",
            profileImageUrl = user?.photoUrl,
            notificationCount = notificationCount,
            navController = navController
        )
        Spacer(Modifier.height(22.dp))

        when (val state = weatherState) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = Color.Red
                )
            }
            is Resource.Success -> {
                val currentWeather = state.data.current
                WeatherCard(
                    currentWeather = currentWeather,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        SystemHealthStatus(modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        EnergyMetricsGrid()
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SolarEaseTheme {
        HomeScreen(
            notificationCount = 3,
            navController = rememberNavController()
        )
    }
}
