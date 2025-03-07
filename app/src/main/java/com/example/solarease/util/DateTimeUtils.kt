package com.example.solarease.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.formatDateTime(): String {
    return DateTimeFormatter.ofPattern("MMM dd, HH:mm")
        .withZone(ZoneId.systemDefault())
        .format(this)
}
