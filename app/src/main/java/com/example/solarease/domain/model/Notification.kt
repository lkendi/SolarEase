package com.example.solarease.domain.model

import java.time.Instant

data class Notification(
    val id: String,
    val title: String,
    val type: NotificationType,
    val message: String,
    val timestamp: Instant,
    val isRead: Boolean = false
)

enum class NotificationType { ALERT, WARNING, INFO }