package com.app.solarease.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface IoTDataApiService {
    @GET("api/GetHistoricalTelemetry")
    suspend fun getHistorical(
        @Query("deviceId") deviceId: String,
        @Query("from") fromIso: String,
        @Query("to") toIso: String,
        @Query("limit") limit: Int,
        @Query("code") functionKey: String
    ): List<IoTDataDto>
}
