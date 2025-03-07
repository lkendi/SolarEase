package com.example.solarease.domain.usecases

import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.repository.EnergyRepository
import javax.inject.Inject

class GetFaultLogsUseCase @Inject constructor(
    private val repository: EnergyRepository
) {
    suspend operator fun invoke(): List<FaultLog> {
        return repository.getFaultLogs()
    }
}
