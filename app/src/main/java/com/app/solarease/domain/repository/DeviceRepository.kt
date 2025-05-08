package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device

interface DeviceRepository {
    suspend fun getDevices(forceRefresh: Boolean): Resource<List<Device>>
}
