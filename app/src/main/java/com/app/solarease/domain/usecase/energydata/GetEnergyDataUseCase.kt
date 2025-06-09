package com.app.solarease.domain.usecase.energydata

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.TimeInterval
import com.app.solarease.domain.repository.EnergyRepository
import javax.inject.Inject

class GetEnergyDataUseCase @Inject constructor(
    private val repository: EnergyRepository
) {
    suspend operator fun invoke(interval: TimeInterval): Resource<EnergyData> {
        return repository.getEnergyData(interval)
    }
}
