package com.example.solarease.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.home.components.EnergyMetricsGrid
import com.example.solarease.presentation.home.components.GreetingSection
import com.example.solarease.presentation.home.components.SystemHealthStatus
import com.example.solarease.presentation.home.components.WeatherCard

@Composable
fun HomeScreen(
    userName: String = "User Name",
    notificationCount: Int = 2,
    onNotificationClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        GreetingSection(
            userName = userName,
            notificationCount = notificationCount,
            onNotificationClick = onNotificationClick
        )
        Spacer(modifier = Modifier.height(32.dp))
        WeatherCard()
        Spacer(modifier = Modifier.height(32.dp))
        SystemHealthStatus()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Energy Overview",
            style = Typography.titleLarge,
            color = White
        )
        Spacer(modifier = Modifier.height(16.dp))
        EnergyMetricsGrid()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SolarEaseTheme {
        HomeScreen()
    }
}
