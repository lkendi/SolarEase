package com.app.solarease.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.components.IconWithBackground
import com.app.solarease.presentation.common.theme.SolarYellow
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.User

@Composable
fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    icon: ImageVector,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconWithBackground(
                icon = icon,
                backgroundColor = SolarYellow,
                title = "Setting",
                iconColor = SolarYellow,
                backgroundSize = 50.dp,
                backgroundColorAlpha = 0.2f
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }
        }

        when {
            trailing != null -> trailing()
            onClick != null  -> Icon(
                imageVector = TablerIcons.ChevronRight,
                contentDescription = "Go",
                tint = Color.White.copy(alpha = 0.6f)
            )
            else  -> {}
        }
    }

    HorizontalDivider(
        color = SolarYellow.copy(alpha = 0.3f),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Preview
@Composable
fun SettingsItemPreview() {
    Column {
        SettingsItem(
            title = "Profile & Security",
            icon = TablerIcons.User,
            onClick = { /* navigate */ }
        )
        SettingsItem(
            title = "Notifications",
            icon = TablerIcons.User,
            trailing = { Switch(checked = true, onCheckedChange = {}) }
        )
    }
}
