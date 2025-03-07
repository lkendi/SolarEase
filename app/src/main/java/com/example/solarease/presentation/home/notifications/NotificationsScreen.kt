package com.example.solarease.presentation.home.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.solarease.domain.model.Notification
import com.example.solarease.domain.model.NotificationType
import com.example.solarease.presentation.common.components.IconWithBackground
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.BellOff
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun NotificationsScreen(viewModel: NotificationViewModel = hiltViewModel()) {
    val notifications by viewModel.notifications.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notifications",
                    style = Typography.headlineMedium,
                    color = White
                )
                IconButton(
                    onClick = { viewModel.clearAllNotifications() },
                    enabled = notifications.isNotEmpty()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear All",
                        tint = Yellow
                    )
                }
            }
            NotificationList(
                notifications = notifications,
                onNotificationClick = { viewModel.markAsRead(it.id) },
                onDelete = { viewModel.deleteNotification(it.id) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NotificationList(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    onDelete: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    if (notifications.isEmpty()) {
        EmptyNotificationsPlaceholder()
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(notifications, key = { it.id }) { notification ->
                NotificationItem(
                    notification = notification,
                    onClick = { onNotificationClick(notification) },
                    onDelete = { onDelete(notification) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Yellow.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NotificationItem(
    notification: Notification,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberDismissState()
    SwipeToDismiss(
        state = dismissState,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

            }
        },
        directions = setOf(DismissDirection.EndToStart),
        dismissContent = {
            NotificationItemContent(
                notification = notification,
                onClick = onClick,
                modifier = Modifier.clickable { onClick() }
            )
        }
    )
    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        LaunchedEffect(Unit) { onDelete() }
    }
}

@Composable
private fun NotificationItemContent(
    notification: Notification,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NotificationIcon(notification.type)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            NotificationHeader(notification)
            Text(
                text = notification.message,
                style = Typography.bodyMedium,
                color = White.copy(alpha = 0.8f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        NotificationTimestamp(notification.timestamp)
    }
}

@Composable
private fun NotificationIcon(type: NotificationType) {
    val (icon, tint) = when (type) {
        NotificationType.ALERT -> Pair(Icons.Default.Warning, ErrorRed.copy(alpha = 0.8f))
        NotificationType.WARNING -> Pair(Icons.Default.Info, EnergyOrange.copy(alpha = 0.8f))
        NotificationType.INFO -> Pair(Icons.Default.Notifications, SolarBlue.copy(alpha = 0.8f))
    }
    IconWithBackground(
        icon = icon,
        title = "Alert",
        iconColor = tint,
        backgroundSize = 50.dp,
        backgroundColor = DarkGrey,
        backgroundColorAlpha = 0.6f
    )
}

@Composable
private fun NotificationHeader(notification: Notification) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (!notification.isRead) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = androidx.compose.foundation.shape.CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = notification.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NotificationTimestamp(timestamp: Instant) {
    val timeText = remember(timestamp) {
        DateTimeFormatter.ofPattern("HH:mm")
            .format(timestamp.atZone(ZoneId.systemDefault()))
    }
    Text(
        text = timeText,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
private fun EmptyNotificationsPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = TablerIcons.BellOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No New Notifications",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    SolarEaseTheme {
        NotificationsScreen()
    }
}
