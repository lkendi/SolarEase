package com.example.solarease.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.settings.components.SettingsRow
import compose.icons.TablerIcons
import compose.icons.tablericons.InfoCircle
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Message
import compose.icons.tablericons.Tools

@Composable
fun SettingsScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Settings",
                color = White,
                style = Typography.titleMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            SettingsRow(
                title = "System Settings",
                icon = TablerIcons.Tools,
                onClick = { /* TODO: Navigate */ }
            )

            SettingsRow(
                title = "Solar Details",
                icon = TablerIcons.InfoCircle,
                onClick = { /* TODO: Navigate */ }
            )

            SettingsRow(
                title = "Give Feedback",
                icon = TablerIcons.Message,
                onClick = { /* TODO: Navigate */ }
            )

            SettingsRow(
                title = "Log Out",
                icon = TablerIcons.Logout,
                onClick = { /* TODO: Sign out */ }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview()
{
    SolarEaseTheme {
        SettingsScreen()
    }
}
