package com.app.solarease.data.repository

import android.util.Log
import com.app.solarease.common.Resource
import com.app.solarease.data.remote.IoTDataApiService
import com.app.solarease.domain.model.IoTData
import com.app.solarease.domain.repository.IoTDataRepository
import javax.inject.Inject

class IoTDataRepositoryImpl @Inject constructor(
    private val api: IoTDataApiService
) : IoTDataRepository {
    private val TAG = "IoTDataRepositoryImpl"

    override suspend fun fetchIoTData(deviceId: String): Resource<List<IoTData>> {
        return try {
            val dtos = api.getIoTData(deviceId)
            Log.d(TAG, "Fetched ${dtos.size} IoTDataDto for deviceId: $deviceId, data: $dtos")
            val domainList = dtos.map { it.toDomain() }
            Log.d(TAG, "Mapped to ${domainList.size} IoTData for deviceId: $deviceId, data: $domainList")
            Resource.Success(domainList)
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Unknown error"
            Log.e(TAG, "Failed to fetch IoT data for deviceId: $deviceId, error: $errorMessage", e)
            Resource.Error(errorMessage)
        }
    }
}