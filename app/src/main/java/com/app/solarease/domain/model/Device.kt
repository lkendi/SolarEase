package com.app.solarease.domain.model

data class Device(
    val id: String,
    val name: String,
    val type: DeviceType,
    val status: DeviceStatus,
    val powerOutput: Double? = null,
    val efficiency: Double? = null,
    val capacity: Int? = null
)

enum class DeviceType {
    SOLAR_PANEL,
    INVERTER,
    BATTERY
}


enum class DeviceStatus {
    ONLINE, OFFLINE, WARNING
}
