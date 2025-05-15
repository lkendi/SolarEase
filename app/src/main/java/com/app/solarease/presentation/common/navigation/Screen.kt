package com.app.solarease.presentation.common.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object LocationPermission: Screen("location_permission")
    data object Home : Screen("home")
    data object Notifications : Screen("notifications")
    data object Devices : Screen("devices")
    data object Panels : Screen("panels")
    data object Inverter : Screen("inverter")
    data object Battery : Screen("battery")
    data object Reports : Screen("reports")
    data object Faults : Screen("faults")
    data object StatusHistory: Screen("status_history")
    data object Settings : Screen("settings")
    data object SystemDetails : Screen("system_details")
}
