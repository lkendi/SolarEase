package com.app.solarease.data.repository

import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.EnergyDataPoint
import com.app.solarease.domain.model.EnergyStats
import com.app.solarease.domain.model.FaultLog
import com.app.solarease.domain.model.FaultSeverity
import com.app.solarease.domain.model.TimeInterval
import com.app.solarease.domain.repository.EnergyRepository
import kotlinx.coroutines.delay
import java.time.Instant
import kotlin.random.Random

class FakeEnergyRepository : EnergyRepository {
    override suspend fun getEnergyData(interval: TimeInterval): EnergyData {
        delay(1000)
        return EnergyData(
            generation = EnergyStats(
                current = Random.nextDouble(100.0, 500.0),
                previous = Random.nextDouble(80.0, 450.0),
                unit = "kWh"
            ),
            consumption = EnergyStats(
                current = Random.nextDouble(80.0, 400.0),
                previous = Random.nextDouble(70.0, 380.0),
                unit = "kWh"
            ),
            timeSeries = List(24) { index ->
                EnergyDataPoint(
                    timestamp = Instant.now().minusSeconds((index * 3600).toLong()).toString(),
                    generation = Random.nextDouble(0.5, 5.0),
                    consumption = Random.nextDouble(0.5, 5.0),
                )
            },
            netBalance = Random.nextDouble(-50.0, 200.0),
            systemEfficiency = 92.1
        )
    }

    override suspend fun getFaultLogs(): List<FaultLog> {
        return listOf(
            FaultLog(
                id = "1",
                title = "Simulated Warning",
                description = "System operating normally",
                timestamp = Instant.now(),
                severity = FaultSeverity.INFO,
                errorCode = "ERR-01"
            )
        )
    }
}
