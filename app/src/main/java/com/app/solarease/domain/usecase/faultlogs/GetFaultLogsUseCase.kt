package com.app.solarease.domain.usecase.faultlogs

import com.app.solarease.domain.model.FaultLog
import com.app.solarease.domain.repository.EnergyRepository
import javax.inject.Inject

class GetFaultLogsUseCase @Inject constructor(
    private val repository: EnergyRepository
) {
    suspend operator fun invoke(): List<FaultLog> {
        return repository.getFaultLogs()
    }
}
