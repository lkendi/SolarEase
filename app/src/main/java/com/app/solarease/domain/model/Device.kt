package com.app.solarease.domain.model

sealed class Device {
    abstract val id: String
    abstract val deviceType: String
    abstract val model: String
    abstract val manufacturer: String
    abstract val status: String?
    abstract val serialNumber: String

    data class Inverter(
        override val id: String = "",
        override val deviceType: String = "inverter",
        override val model: String = "",
        override val manufacturer: String = "",
        override val status: String = "",
        override val serialNumber: String,
        val acInputCurrent: String?,
        val acInputFrequency: String?,
        val acInputVoltage: String?,
        val inverterType: String?,
        val dcInputCurrent: String?,
        val dcInputFrequency: String?,
        val dcInputVoltage: String?,
        val inverterModeCurrent: String?,
        val inverterModeFrequency: String?,
        val inverterModeVoltage: String?,
        val capacity: String,
        val chargingVoltage: String?,
        val peakPower: String?,
        val solarChargingMode: String?,
        val solarInputMaxCurrent: String?,
        val solarInputMaxVoltage: String?,
        val solarInputVoltageRange: String?,
    ) : Device()

    data class Battery(
        override val id: String,
        override val deviceType: String = "battery",
        override val model: String,
        override val manufacturer: String,
        override val status: String?,
        override val serialNumber: String,
        val capacity: Double,
        val voltage: Double,
        val chargeCycles: Int,
        val stateOfCharge: Float? = null
    ) : Device()

    data class Panel(
        override val id: String,
        override val deviceType: String = "panel",
        override val model: String,
        override val manufacturer: String,
        override val status: String?,
        override val serialNumber: String,
        val capacity: Double,
        val efficiency: Double,
        val tilt: Double,
        val azimuth: Double,
        val powerOutput: Double? = null
    ) : Device()
}
