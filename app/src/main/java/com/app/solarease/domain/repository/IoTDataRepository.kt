package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.IoTData

interface IoTDataRepository {
    suspend fun fetchIoTData(deviceId: String): Resource<List<IoTData>>
}
