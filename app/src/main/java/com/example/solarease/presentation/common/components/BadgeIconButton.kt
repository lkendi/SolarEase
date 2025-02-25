package com.example.solarease.presentation.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.Yellow

@Composable
fun BadgeIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeCount: Int = 0,
) {
    BadgedBox(
        badge = {
            if (badgeCount > 0) {
                Badge {
                    Text(text = badgeCount.toString())
                }
            }
        }
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            IconWithBackground(
                icon = icon,
                title = "Notification",
                iconColor = Yellow,
                backgroundSize = 50.dp,
                backgroundColor = DarkGrey,
                backgroundColorAlpha = 0.6f,
                modifier = Modifier.size(70.dp)
            )
        }
    }
}

@Preview
@Composable
fun BadgeIconButtonPreview() {
    BadgeIconButton(
        icon = Icons.Default.Notifications,
        onClick = { /* Handle click */ },
        badgeCount = 5
    )
}