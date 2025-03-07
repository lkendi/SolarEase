package com.example.solarease.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.solarease.domain.model.DeviceType
import com.example.solarease.presentation.common.components.BottomNavigationBar
import com.example.solarease.presentation.devices.DevicesScreen
import com.example.solarease.presentation.devices.battery.BatteryScreen
import com.example.solarease.presentation.devices.panels.PanelsWeatherScreen
import com.example.solarease.presentation.home.HomeScreen
import com.example.solarease.presentation.home.notifications.NotificationsScreen
import com.example.solarease.presentation.reports.ReportsScreen
import com.example.solarease.presentation.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar { selectedTab ->
                when (selectedTab) {
                    "Home" -> navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    "Reports" -> navController.navigate(Screen.Reports.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    "Devices" -> navController.navigate(Screen.Devices.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    "Settings" -> navController.navigate(Screen.Settings.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Home.route) {
                HomeScreen(onNotificationClick = { navController.navigate(Screen.Notifications.route) })
            }
            composable(Screen.Reports.route) { ReportsScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.Devices.route) {
                DevicesScreen(onDeviceClick = { device ->
                    if (device.type == DeviceType.BATTERY) {
                        navController.navigate(Screen.Battery.route)
                    } else if (device.type == DeviceType.SOLAR_PANEL) {
                        navController.navigate(Screen.Panels.route)
                    }

                })
            }
            composable(Screen.Battery.route) { BatteryScreen() }
            composable(Screen.Panels.route) { PanelsWeatherScreen() }
            composable(Screen.Notifications.route) { NotificationsScreen() }
        }
    }
}

