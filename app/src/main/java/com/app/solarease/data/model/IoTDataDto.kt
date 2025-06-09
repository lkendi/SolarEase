package com.app.solarease.data.model

import com.app.solarease.domain.model.IoTData
import com.squareup.moshi.Json

data class IoTDataDto(
    @Json(name = "id") val id: String,
    @Json(name = "deviceId") val deviceId: String,
    @Json(name = "solarVoltage") val solarVoltage: Double,
    @Json(name = "batteryVoltage") val batteryVoltage: Double,
    @Json(name = "loadCurrent") val loadCurrent: Double,
    @Json(name = "batteryTemperature") val batteryTemperature: Double,
    @Json(name = "timestamp") val timestamp: String
) {
    fun toDomain(): IoTData = IoTData(
        id = id,
        deviceId = deviceId,
        solarVoltage = solarVoltage,
        batteryVoltage = batteryVoltage,
        loadCurrent = loadCurrent,
        batteryTemperature = batteryTemperature,
        timestamp = timestamp
    )
}
