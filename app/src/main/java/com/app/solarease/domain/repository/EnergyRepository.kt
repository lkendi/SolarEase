package com.app.solarease.domain.repository

import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.FaultLog
import com.app.solarease.domain.model.TimeInterval

interface EnergyRepository {
    suspend fun getEnergyData(interval: TimeInterval): EnergyData
    suspend fun getFaultLogs(): List<FaultLog>
}
