package com.app.solarease.domain.model

import java.time.Instant

data class EnergyData(
    val generation: EnergyStats,
    val consumption: EnergyStats,
    val timeSeries: List<EnergyDataPoint>,
    val netBalance: Double,
    val systemEfficiency: Double
)

data class EnergyStats(
    val current: Double,
    val previous: Double,
    val unit: String = "kWh"
)

data class EnergyDataPoint(
    val timestamp: String,
    val generation: Double,
    val consumption: Double,
    val value: Double = 0.0,
)

enum class TimeInterval { DAILY, WEEKLY, MONTHLY, YEARLY }

data class FaultLog(
    val id: String,
    val title: String,
    val timestamp: Instant,
    val description: String,
    val severity: FaultSeverity
)

enum class FaultSeverity { INFO, WARNING, CRITICAL }