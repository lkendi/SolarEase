package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device

interface DeviceRepository {
    suspend fun getDevices(): Resource<List<Device>>
    suspend fun getDeviceDetails(id: String): Resource<Device>
}
