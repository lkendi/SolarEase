package com.app.solarease.domain.model

import java.time.Instant
import java.util.UUID

data class Notification(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val type: NotificationType = NotificationType.DEFAULT,
    val timestamp: Instant
)

enum class NotificationType {
    ALERT, INFO, DEFAULT
}
