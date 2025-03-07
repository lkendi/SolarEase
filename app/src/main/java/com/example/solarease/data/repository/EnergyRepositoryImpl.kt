package com.example.solarease.data.repository

import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.domain.repository.EnergyRepository
import javax.inject.Inject

class EnergyRepositoryImpl @Inject constructor(

) : EnergyRepository {
    override suspend fun getEnergyData(interval: TimeInterval): EnergyData {
        TODO("Not yet implemented")
    }

    override suspend fun getFaultLogs(): List<FaultLog> {
        TODO("Not yet implemented")
    }
}
