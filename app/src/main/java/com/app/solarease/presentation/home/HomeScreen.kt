package com.app.solarease.presentation.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.presentation.auth.AuthState
import com.app.solarease.presentation.auth.AuthViewModel
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.home.components.EnergyMetricsGrid
import com.app.solarease.presentation.home.components.GreetingSection
import com.app.solarease.presentation.home.components.ProductionCard
import com.app.solarease.presentation.home.components.SystemHealthStatus
import com.app.solarease.presentation.home.components.WeatherCard

@Composable
fun HomeScreen(
    notificationCount: Int = 0,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val user = (authState as? AuthState.Authenticated)?.user
    val userName = user?.displayName ?: "User"
    val profileImageUrl = user?.photoUrl

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GreetingSection(
            userName = userName,
            profileImageUrl = profileImageUrl,
            notificationCount = notificationCount,
            navController = navController
        )
        Spacer(Modifier.height(22.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeatherCard(
                weatherCondition = "Rainy",
                subtitle = "Peak production",
                modifier = Modifier.weight(1f)
            )
            ProductionCard(
                title = "Today's Production",
                value = "85.3 kWh",
                subtitle = "Total Generated",
                modifier = Modifier.weight(1f)
            )
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
