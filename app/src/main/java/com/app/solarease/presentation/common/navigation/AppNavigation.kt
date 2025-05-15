package com.app.solarease.presentation.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.solarease.domain.model.Device
import com.app.solarease.presentation.common.components.BottomNavigationBar
import com.app.solarease.presentation.devices.BatteryScreen
import com.app.solarease.presentation.devices.DevicesScreen
import com.app.solarease.presentation.devices.inverter.FaultLogsScreen
import com.app.solarease.presentation.devices.inverter.InverterScreen
import com.app.solarease.presentation.devices.inverter.StatusHistoryScreen
import com.app.solarease.presentation.devices.panels.PanelsWeatherScreen
import com.app.solarease.presentation.home.HomeScreen
import com.app.solarease.presentation.home.NotificationsScreen
import com.app.solarease.presentation.onboarding.LocationPermissionScreen
import com.app.solarease.presentation.onboarding.OnboardingScreen
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
            if (currentRoute != Screen.Onboarding.route && currentRoute != Screen.LocationPermission.route) {
                BottomNavigationBar { tab ->
                    navigateToTab(navController, tab)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(navController = navController)
            }
            composable(Screen.LocationPermission.route) {
                LocationPermissionScreen(navController = navController)
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
            composable(Screen.Devices.route) {
                DevicesScreen(
                    onDeviceClick = { device ->
                        when (device) {
                            is Device.Battery -> navController.navigate(Screen.Battery.route)
                            is Device.Inverter -> navController.navigate(Screen.Inverter.route)
                            is Device.Panel -> navController.navigate(Screen.Panels.route)
                        }
                    }
                )
            }
            composable(Screen.Battery.route) {
                BatteryScreen(navController = navController)
            }
            composable(Screen.Inverter.route) {
                InverterScreen(navController = navController)
            }
            composable(Screen.Faults.route) {
                FaultLogsScreen(navController = navController)
            }
            composable(Screen.StatusHistory.route) {
                StatusHistoryScreen(navController = navController)
            }
            composable(Screen.Panels.route) {
                PanelsWeatherScreen(navController = navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
            }
            composable(Screen.SystemDetails.route) {
                SystemDetailsScreen(navController = navController)
            }
        }
    }
}

private fun navigateToTab(navController: NavHostController, tab: String) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val isDevicesChild = currentRoute in listOf(
        Screen.Battery.route,
        Screen.Inverter.route,
        Screen.Faults.route,
        Screen.StatusHistory.route,
        Screen.Panels.route
    )

    val popUpToRoute = when {
        isDevicesChild -> Screen.Devices.route
        else -> currentRoute ?: navController.graph.startDestinationId.toString()
    }

    when (tab) {
        "Home" -> navController.navigate(Screen.Home.route) {
            popUpTo(popUpToRoute) { inclusive = isDevicesChild }
            launchSingleTop = true
            restoreState = true
        }
        "Reports" -> navController.navigate(Screen.Reports.route) {
            popUpTo(popUpToRoute) { inclusive = isDevicesChild }
            launchSingleTop = true
            restoreState = true
        }
        "Devices" -> navController.navigate(Screen.Devices.route) {
            popUpTo(popUpToRoute) { inclusive = isDevicesChild }
            launchSingleTop = true
            restoreState = true
        }
        "Settings" -> navController.navigate(Screen.Settings.route) {
            popUpTo(popUpToRoute) { inclusive = isDevicesChild }
            launchSingleTop = true
            restoreState = true
        }
    }
}
