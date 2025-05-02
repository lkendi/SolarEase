package com.app.solarease.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import compose.icons.TablerIcons
import compose.icons.tablericons.ChartDonut
import compose.icons.tablericons.ChartDonut2
import compose.icons.tablericons.Outlet
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
        BottomNavigationItem("Home", TablerIcons.SmartHome, TablerIcons.SmartHome),
        BottomNavigationItem("Devices", TablerIcons.Outlet, TablerIcons.Outlet),
        BottomNavigationItem("Reports", TablerIcons.ChartDonut, TablerIcons.ChartDonut2),
        BottomNavigationItem("Settings", TablerIcons.SettingsAutomation, TablerIcons.Settings)
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        contentColor = SolarYellow,
        tonalElevation = 4.dp
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
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor        = SolarYellow.copy(alpha = 0.1f),
                    selectedIconColor     = SolarYellow,
                    selectedTextColor     = SolarYellow,
                    unselectedIconColor   = Color.White.copy(alpha = 0.7f),
                    unselectedTextColor   = Color.White.copy(alpha = 0.7f)
                )
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
