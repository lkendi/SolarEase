package com.example.solarease.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Battery
import compose.icons.tablericons.BatteryCharging
import compose.icons.tablericons.ChartBar
import compose.icons.tablericons.ChartCandle
import compose.icons.tablericons.Power
import compose.icons.tablericons.Settings
import compose.icons.tablericons.SettingsAutomation
import compose.icons.tablericons.SmartHome

@Composable
fun BottomNavigationBar(
    onTabSelected: (String) -> Unit = {}
) {
    data class BottomNavigationItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
    )

    var selectedTab by remember { mutableStateOf("Home") }

    val items = listOf(
        BottomNavigationItem("Home", TablerIcons.SmartHome, Icons.Outlined.Home),
        BottomNavigationItem("Panels", TablerIcons.Power, Icons.Outlined.PlayArrow),
        BottomNavigationItem("Battery", TablerIcons.Battery, TablerIcons.BatteryCharging),
        BottomNavigationItem("Reports", TablerIcons.ChartCandle, TablerIcons.ChartBar),
        BottomNavigationItem("Settings", TablerIcons.SettingsAutomation, TablerIcons.Settings)
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item.title,
                onClick = {
                    selectedTab = item.title
                    onTabSelected(item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == item.title) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    SolarEaseTheme {
        BottomNavigationBar()
    }
}
