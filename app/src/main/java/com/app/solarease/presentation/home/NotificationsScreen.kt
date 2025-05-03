package com.app.solarease.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.domain.model.Notification
import com.app.solarease.domain.model.NotificationType
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.utils.formatDateTime
import compose.icons.TablerIcons
import compose.icons.tablericons.AlertCircle
import compose.icons.tablericons.BellOff
import compose.icons.tablericons.InfoCircle
import java.time.Instant

@Composable
fun NotificationsScreen(
    notifications: List<Notification> = emptyList(),
    onNotificationClick: (Notification) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Notifications",
            style = Typography.headlineLarge,
            color = White,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        if (notifications.isEmpty()) {
            EmptyNotificationsIndicator()
        } else {
            NotificationsList(notifications, onNotificationClick)
        }
    }
}

@Composable
private fun NotificationsList(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications) { notification ->
            NotificationItem(
                notification = notification,
                onClick = { onNotificationClick(notification) }
            )
            HorizontalDivider(color = SolarYellow.copy(alpha = 0.4f))
        }
    }
}

@Composable
private fun EmptyNotificationsIndicator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = TablerIcons.BellOff,
            contentDescription = "No notifications",
            tint = SolarYellow,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No New Notifications",
            style = Typography.titleMedium,
            color = White.copy(alpha = 0.8f)
        )

        Text(
            text = "You're all caught up!",
            style = Typography.bodyMedium,
            color = White.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when (notification.type) {
                NotificationType.ALERT -> TablerIcons.AlertCircle
                else -> TablerIcons.InfoCircle
            },
            contentDescription = null,
            tint = when (notification.type) {
                NotificationType.ALERT -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.primary
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(notification.title, style = Typography.bodyLarge, color = White)
            Text(notification.message, style = Typography.bodyMedium, color = White)
            Text(
                notification.timestamp.formatDateTime(),
                style = Typography.labelSmall, color = White
            )
        }
    }
}

@Preview(showBackground = true, name = "With Notifications")
@Composable
fun NotificationsScreenPreview() {
    val samples = listOf(
        Notification("1","Battery Low","Battery <20%",NotificationType.ALERT, Instant.now()),
        Notification("2","System Update","Firmware up to date", NotificationType.INFO, Instant.now())
    )
    SolarEaseTheme {
        NotificationsScreen(notifications = samples)
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun EmptyNotificationsScreenPreview() {
    SolarEaseTheme {
        NotificationsScreen(notifications = emptyList())
    }
}
