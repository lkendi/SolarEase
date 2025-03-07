package com.example.solarease.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.BackgroundGrey
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.ChartPie
import compose.icons.tablericons.ChartPie2
import compose.icons.tablericons.Home
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
        BottomNavigationItem("Home", TablerIcons.SmartHome, TablerIcons.Home),
        BottomNavigationItem("Devices", TablerIcons.Outlet , TablerIcons.Outlet),
        BottomNavigationItem("Reports", TablerIcons.ChartPie, TablerIcons.ChartPie2),
        BottomNavigationItem("Settings", TablerIcons.SettingsAutomation, TablerIcons.Settings)
    )
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = BackgroundGrey,
        contentColor = MaterialTheme.colorScheme.onSurface
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
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Yellow,
                    selectedTextColor = Yellow,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Yellow.copy(alpha = 0.2f)
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
