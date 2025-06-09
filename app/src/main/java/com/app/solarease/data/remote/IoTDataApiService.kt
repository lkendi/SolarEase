package com.app.solarease.data.remote

import com.app.solarease.R
import com.app.solarease.data.model.IoTDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface IoTDataApiService {
    @GET("api/GetHistoricalTelemetry")
    suspend fun getIoTData(
        @Query("deviceId") deviceId: String,
        @Query("code") code: String = R.string.azure_api_code.toString()
    ): List<IoTDataDto>
}
