package com.example.solarease.domain.repository

import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.model.TimeInterval

interface EnergyRepository {
    suspend fun getEnergyData(interval: TimeInterval): EnergyData
    suspend fun getFaultLogs(): List<FaultLog>
}
