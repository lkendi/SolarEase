package com.app.solarease.data.mapper

import com.app.solarease.data.model.DeviceDto
import com.app.solarease.domain.model.Device

fun DeviceDto.toDomain(): Device {
    return when (this) {
        is DeviceDto.InverterDto -> Device.Inverter(
            id = id,
            deviceType = deviceType,
            model = model,
            manufacturer = manufacturer,
            status = status,
            serialNumber = serialNumber,
            capacity = capacity,
            acInputCurrent = acInputCurrent,
            acInputFrequency = acInputFrequency,
            acInputVoltage = acInputVoltage,
            inverterType = inverterType,
            dcInputCurrent = dcInputCurrent,
            dcInputFrequency = dcInputFrequency,
            dcInputVoltage = dcInputVoltage,
            inverterModeCurrent = inverterModeCurrent,
            inverterModeFrequency = inverterModeFrequency,
            inverterModeVoltage = inverterModeVoltage,
            chargingVoltage = chargingVoltage,
            peakPower = peakPower,
            solarChargingMode = solarChargingMode,
            solarInputMaxCurrent = solarInputMaxCurrent,
            solarInputMaxVoltage = solarInputMaxVoltage,
            solarInputVoltageRange = solarInputVoltageRange
        )
        is DeviceDto.BatteryDto -> Device.Battery(
            id = id,
            deviceType = deviceType,
            model = model,
            manufacturer = manufacturer,
            status = status,
            serialNumber = serialNumber,
            capacity = capacity.toDouble(),
            voltage = voltage,
            chargeCycles = chargeCycles
        )
        is DeviceDto.PanelDto -> Device.Panel(
            id = id,
            deviceType = deviceType,
            model = model,
            manufacturer = manufacturer,
            status = status,
            serialNumber = serialNumber,
            capacity = capacity.toDouble(),
            efficiency = efficiency,
            tilt = tilt,
            azimuth = azimuth
        )
    }
}
