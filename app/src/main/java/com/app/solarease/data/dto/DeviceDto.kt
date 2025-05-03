package com.app.solarease.data.dto

import android.util.Log

sealed class DeviceDto {
    abstract val id: String
    abstract val deviceType: String
    abstract val model: String
    abstract val manufacturer: String
    abstract val status: String
    abstract val serialNumber: String

    data class InverterDto @JvmOverloads constructor(
        override val id: String = "",
        override val deviceType: String = "inverter",
        override val model: String = "",
        override val manufacturer: String = "",
        override val status: String = "unknown",
        override val serialNumber: String = "",
        val acInputCurrent: String? = null,
        val acInputFrequency: String? = null,
        val acInputVoltage: String? = null,
        val inverterType: String? = null,
        val dcInputCurrent: String? = null,
        val dcInputFrequency: String? = null,
        val dcInputVoltage: String? = null,
        val inverterModeCurrent: String? = null,
        val inverterModeFrequency: String? = null,
        val inverterModeVoltage: String? = null,
        val capacity: String = "0",
        val chargingVoltage: String? = null,
        val peakPower: String? = null,
        val solarChargingMode: String? = null,
        val solarInputMaxCurrent: String? = null,
        val solarInputMaxVoltage: String? = null,
        val solarInputVoltageRange: String? = null
    ) : DeviceDto() {
        init {
            Log.d("InverterDto", "Deserialized: id=$id, deviceType=$deviceType, model=$model, manufacturer=$manufacturer, status=$status, serialNumber=$serialNumber, capacity=$capacity")
            if (capacity == "0" || status == "unknown") {
                Log.w("InverterDto", "Missing or default fields: capacity=$capacity, status=$status")
            }
        }
    }

    data class BatteryDto @JvmOverloads constructor(
        override val id: String = "",
        override val deviceType: String = "battery",
        override val model: String = "",
        override val manufacturer: String = "",
        override val status: String = "unknown",
        override val serialNumber: String = "",
        val capacity: Double = 0.0,
        val voltage: Double = 0.0,
        val chargeCycles: Int = 0
    ) : DeviceDto() {
        init {
            Log.d("BatteryDto", "Deserialized: id=$id, deviceType=$deviceType, model=$model, manufacturer=$manufacturer, status=$status, serialNumber=$serialNumber, capacity=$capacity")
            if (status == "unknown") {
                Log.w("BatteryDto", "Missing or default fields: status=$status")
            }
        }
    }

    data class PanelDto @JvmOverloads constructor(
        override val id: String = "",
        override val deviceType: String = "panel",
        override val model: String = "",
        override val manufacturer: String = "",
        override val status: String = "unknown",
        override val serialNumber: String = "",
        val capacity: Double = 0.0,
        val efficiency: Double = 0.0,
        val tilt: Double = 0.0,
        val azimuth: Double = 0.0
    ) : DeviceDto() {
        init {
            Log.d("PanelDto", "Deserialized: id=$id, deviceType=$deviceType, model=$model, manufacturer=$manufacturer, status=$status, serialNumber=$serialNumber, capacity=$capacity")
            if (status == "unknown") {
                Log.w("PanelDto", "Missing or default fields: status=$status")
            }
        }
    }
}
