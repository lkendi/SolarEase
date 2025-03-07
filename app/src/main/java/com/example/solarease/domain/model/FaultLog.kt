package com.example.solarease.domain.model

import java.time.Instant

data class FaultLog(
    val id: String,
    val faultCode: String?,
    val title: String,
    val description: String,
    val timestamp: Instant,
    val severity: FaultSeverity
)

enum class FaultSeverity { INFO, WARNING, CRITICAL }
