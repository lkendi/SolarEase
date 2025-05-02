package com.app.solarease.presentation.common.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.formatDateTime(): String {
    return DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
        .withZone(ZoneId.systemDefault())
        .format(this)
}
