package com.app.solarease.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.solarease.presentation.common.components.BottomNavigationBar
import com.app.solarease.presentation.devices.BatteryScreen
import com.app.solarease.presentation.devices.DevicesScreen
import com.app.solarease.presentation.devices.InverterScreen
import com.app.solarease.presentation.devices.PanelsWeatherScreen
import com.app.solarease.presentation.home.HomeScreen
import com.app.solarease.presentation.home.NotificationsScreen
import com.app.solarease.presentation.onboarding.OnboardingScreen
import com.app.solarease.presentation.reports.FaultDetailsScreen
import com.app.solarease.presentation.reports.ReportsScreen
import com.app.solarease.presentation.settings.SettingsScreen
import com.app.solarease.presentation.settings.SystemDetailsScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Onboarding.route) {
                BottomNavigationBar { tab ->
                    when (tab) {
                        "Home" -> navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        "Reports" -> navController.navigate(Screen.Reports.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        "Devices" -> navController.navigate(Screen.Devices.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        "Settings" -> navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(Screen.Reports.route) {
                ReportsScreen(navController = navController)
            }
            composable(Screen.Faults.route) {
                FaultDetailsScreen()
            }
            composable(Screen.Devices.route) {
                DevicesScreen(
                    onDeviceClick = {}
                )
            }
            composable(Screen.Battery.route) {
                BatteryScreen()
            }
            composable(Screen.Inverter.route) {
                InverterScreen()
            }
            composable(Screen.Panels.route) {
                PanelsWeatherScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
            }
            composable(Screen.SystemDetails.route) {
                SystemDetailsScreen()
            }
        }
    }
}
