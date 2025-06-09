package com.app.solarease.domain.model

data class IoTData(
    val id: String,
    val deviceId: String,
    val solarVoltage: Double,
    val batteryVoltage: Double,
    val loadCurrent: Double,
    val batteryTemperature: Double,
    val timestamp: String
)
