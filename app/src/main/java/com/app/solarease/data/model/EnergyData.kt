package com.app.solarease.data.model

data class NetworkEnergyData(
    val generation: NetworkEnergyStats,
    val consumption: NetworkEnergyStats,
    val time_series: List<NetworkDataPoint>,
    val net_balance: Double
)

data class NetworkEnergyStats(
    val current: Double,
    val previous: Double,
    val unit: String
)

data class NetworkDataPoint(
    val timestamp: Long,
    val generation: Double,
    val consumption: Double
)
