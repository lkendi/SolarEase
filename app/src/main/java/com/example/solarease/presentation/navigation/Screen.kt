package com.example.solarease.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_route")
    data object Notifications : Screen("notifications_route")
    data object Reports : Screen("reports_route")
    data object Settings : Screen("settings_route")
    data object Devices : Screen("devices_route")
    data object Battery : Screen("battery_route")
    data object Panels : Screen("panels_route")
}
