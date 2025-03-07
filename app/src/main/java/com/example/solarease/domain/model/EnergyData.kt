package com.example.solarease.domain.model

import java.time.Instant

data class EnergyData(
    val generation: EnergyStats,
    val consumption: EnergyStats,
    val timeSeries: List<EnergyDataPoint>,
    val netBalance: Double
) {
    companion object {
        fun empty() = EnergyData(
            generation = EnergyStats(0.0, 0.0, "kWh"),
            consumption = EnergyStats(0.0, 0.0, "kWh"),
            timeSeries = emptyList(),
            netBalance = 0.0
        )
    }
}


data class EnergyStats(
    val current: Double,
    val previous: Double,
    val unit: String = "kWh"
)

data class EnergyDataPoint(
    val timestamp: Instant,
    val generation: Float,
    val consumption: Float,
)

enum class TimeInterval { DAILY, WEEKLY, MONTHLY }
