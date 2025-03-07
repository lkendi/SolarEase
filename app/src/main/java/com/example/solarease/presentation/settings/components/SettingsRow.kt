package com.example.solarease.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.components.IconWithBackground
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronRight

@Composable
fun SettingsRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconWithBackground(
            icon = icon,
            title = title,
            iconColor = Yellow,
            backgroundColor = DarkGrey,
            backgroundColorAlpha = 0.6f,
            backgroundSize = 50.dp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = White,
            style = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = TablerIcons.ChevronRight,
            contentDescription = "Navigate to $title",
            tint = Yellow
        )
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Yellow.copy(alpha = 0.3f))
}

@Preview(showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun SettingsRowPreview(){
    SolarEaseTheme {
        SettingsRow(title = "Account", icon = Icons.Outlined.Person) {
        }
    }
}