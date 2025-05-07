package com.app.solarease.domain.model

import java.time.Instant

data class FaultLog(
    val id: String,
    val title: String,
    val errorCode: String,
    val timestamp: Instant,
    val description: String,
    val severity: FaultSeverity
)

enum class FaultSeverity { INFO, WARNING, CRITICAL }
