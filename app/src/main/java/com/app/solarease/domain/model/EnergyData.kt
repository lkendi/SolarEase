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
    val unit: String
)

data class EnergyDataPoint(
    val timestamp: String,
    val generation: Double,
    val consumption: Double
)

enum class TimeInterval {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

