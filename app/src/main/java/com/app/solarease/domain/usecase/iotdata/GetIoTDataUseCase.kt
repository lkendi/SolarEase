package com.app.solarease.domain.usecase.iotdata

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.IoTData
import com.app.solarease.domain.repository.IoTDataRepository
import javax.inject.Inject

class GetIoTDataUseCase @Inject constructor(
    private val repository: IoTDataRepository
) {
    suspend operator fun invoke(deviceId: String): Resource<IoTData> {
        return when (val result = repository.fetchIoTData(deviceId)) {
            is Resource.Success -> {
                val latestData = result.data.maxByOrNull { it.timestamp }
                if (latestData != null) Resource.Success(latestData)
                else Resource.Error("No IoT data available")
            }
            is Resource.Error -> Resource.Error(result.message)
            is Resource.Loading -> Resource.Loading()
        }
    }
}
