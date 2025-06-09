package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.TimeInterval

interface EnergyRepository {
    suspend fun getEnergyData(interval: TimeInterval): Resource<EnergyData>
}
