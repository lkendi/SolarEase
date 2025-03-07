package com.example.solarease.domain.usecases

import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.domain.repository.EnergyRepository
import javax.inject.Inject

class GetEnergyDataUseCase @Inject constructor(
    private val repository: EnergyRepository
) {
    suspend operator fun invoke(interval: TimeInterval): EnergyData {
        return repository.getEnergyData(interval)
    }
}
