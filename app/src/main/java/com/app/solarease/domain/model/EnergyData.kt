package com.app.solarease.domain.model

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

