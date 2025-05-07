package com.app.solarease.data.model

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

sealed class DeviceDto {
    abstract var id: String
    abstract var deviceType: String
    abstract var model: String
    abstract var manufacturer: String
    abstract var status: String
    abstract var serialNumber: String
    abstract var capacity: String

    @IgnoreExtraProperties
    data class InverterDto @JvmOverloads constructor(
        @get:PropertyName("id")
        @set:PropertyName("id")
        override var id: String = "",

        @get:PropertyName("device_type")
        @set:PropertyName("device_type")
        override var deviceType: String = "inverter",

        @get:PropertyName("model")
        @set:PropertyName("model")
        override var model: String = "",

        @get:PropertyName("manufacturer")
        @set:PropertyName("manufacturer")
        override var manufacturer: String = "",

        @get:PropertyName("status")
        @set:PropertyName("status")
        override var status: String = "unknown",

        @get:PropertyName("serial_number")
        @set:PropertyName("serial_number")
        override var serialNumber: String = "",

        @get:PropertyName("ac_input_current")
        @set:PropertyName("ac_input_current")
        var acInputCurrent: String? = null,

        @get:PropertyName("ac_input_frequency")
        @set:PropertyName("ac_input_frequency")
        var acInputFrequency: String? = null,

        @get:PropertyName("ac_input_voltage")
        @set:PropertyName("ac_input_voltage")
        var acInputVoltage: String? = null,

        @get:PropertyName("inverter_type")
        @set:PropertyName("inverter_type")
        var inverterType: String? = null,

        @get:PropertyName("dc_input_current")
        @set:PropertyName("dc_input_current")
        var dcInputCurrent: String? = null,

        @get:PropertyName("dc_input_frequency")
        @set:PropertyName("dc_input_frequency")
        var dcInputFrequency: String? = null,

        @get:PropertyName("dc_input_voltage")
        @set:PropertyName("dc_input_voltage")
        var dcInputVoltage: String? = null,

        @get:PropertyName("inverter_mode_current")
        @set:PropertyName("inverter_mode_current")
        var inverterModeCurrent: String? = null,

        @get:PropertyName("inverter_mode_frequency")
        @set:PropertyName("inverter_mode_frequency")
        var inverterModeFrequency: String? = null,

        @get:PropertyName("inverter_mode_voltage")
        @set:PropertyName("inverter_mode_voltage")
        var inverterModeVoltage: String? = null,

        @get:PropertyName("capacity")
        @set:PropertyName("capacity")
        override var capacity: String = "0",

        @get:PropertyName("charging_voltage")
        @set:PropertyName("charging_voltage")
        var chargingVoltage: String? = null,

        @get:PropertyName("peak_power")
        @set:PropertyName("peak_power")
        var peakPower: String? = null,

        @get:PropertyName("solar_charging_mode")
        @set:PropertyName("solar_charging_mode")
        var solarChargingMode: String? = null,

        @get:PropertyName("solar_input_max_current")
        @set:PropertyName("solar_input_max_current")
        var solarInputMaxCurrent: String? = null,

        @get:PropertyName("solar_input_max_voltage")
        @set:PropertyName("solar_input_max_voltage")
        var solarInputMaxVoltage: String? = null,

        @get:PropertyName("solar_input_voltage_range")
        @set:PropertyName("solar_input_voltage_range")
        var solarInputVoltageRange: String? = null
    ) : DeviceDto()

    @IgnoreExtraProperties
    data class BatteryDto @JvmOverloads constructor(
        @get:PropertyName("id")
        @set:PropertyName("id")
        override var id: String = "",

        @get:PropertyName("device_type")
        @set:PropertyName("device_type")
        override var deviceType: String = "battery",

        @get:PropertyName("model")
        @set:PropertyName("model")
        override var model: String = "",

        @get:PropertyName("manufacturer")
        @set:PropertyName("manufacturer")
        override var manufacturer: String = "",

        @get:PropertyName("status")
        @set:PropertyName("status")
        override var status: String = "unknown",

        @get:PropertyName("serial_number")
        @set:PropertyName("serial_number")
        override var serialNumber: String = "",

        @get:PropertyName("capacity")
        @set:PropertyName("capacity")
        override var capacity: String = "0",

        @get:PropertyName("voltage")
        @set:PropertyName("voltage")
        var voltage: Double = 0.0,

        @get:PropertyName("charge_cycles")
        @set:PropertyName("charge_cycles")
        var chargeCycles: Int = 0
    ) : DeviceDto()

    @IgnoreExtraProperties
    data class PanelDto @JvmOverloads constructor(
        @get:PropertyName("id")
        @set:PropertyName("id")
        override var id: String = "",

        @get:PropertyName("device_type")
        @set:PropertyName("device_type")
        override var deviceType: String = "panel",

        @get:PropertyName("model")
        @set:PropertyName("model")
        override var model: String = "",

        @get:PropertyName("manufacturer")
        @set:PropertyName("manufacturer")
        override var manufacturer: String = "",

        @get:PropertyName("status")
        @set:PropertyName("status")
        override var status: String = "unknown",

        @get:PropertyName("serial_number")
        @set:PropertyName("serial_number")
        override var serialNumber: String = "",

        @get:PropertyName("capacity")
        @set:PropertyName("capacity")
        override var capacity: String = "0",

        @get:PropertyName("efficiency")
        @set:PropertyName("efficiency")
        var efficiency: Double = 0.0,

        @get:PropertyName("tilt")
        @set:PropertyName("tilt")
        var tilt: Double = 0.0,

        @get:PropertyName("azimuth")
        @set:PropertyName("azimuth")
        var azimuth: Double = 0.0
    ) : DeviceDto()
}
