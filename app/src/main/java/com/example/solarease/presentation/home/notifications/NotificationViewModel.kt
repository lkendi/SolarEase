package com.example.solarease.presentation.home.notifications

import androidx.lifecycle.ViewModel
import com.example.solarease.domain.model.Notification
import com.example.solarease.domain.model.NotificationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    init {
        _notifications.value = listOf(
            Notification(
                id = "1",
                title = "Panel Degradation Alert",
                message = "Panel efficiency dropped 15% this month",
                timestamp = Instant.now(),
                type = NotificationType.ALERT
            ),
            Notification(
                id = "2",
                title = "Optimal Charging",
                message = "Battery storage at 95% - consider selling excess power",
                timestamp = Instant.now(),
                type = NotificationType.INFO
            )
        )
    }

    fun markAsRead(id: String) {
        _notifications.update { list ->
            list.map {
                if (it.id == id) it.copy(isRead = true) else it
            }
        }
    }

    fun deleteNotification(id: String) {
        _notifications.update { list ->
            list.filterNot { it.id == id }
        }
    }

    fun clearAllNotifications() {
        _notifications.update { emptyList() }
    }
}

