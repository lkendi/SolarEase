package com.example.solarease.data.repository

import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.EnergyDataPoint
import com.example.solarease.domain.model.EnergyStats
import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.model.FaultSeverity
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.domain.repository.EnergyRepository
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
            timeSeries = generateFakeTimeSeries(interval),
            netBalance = Random.nextDouble(-50.0, 200.0)
        )
    }

    private fun generateFakeTimeSeries(interval: TimeInterval): List<EnergyDataPoint> {
        val points = when (interval) {
            TimeInterval.DAILY -> 24
            TimeInterval.WEEKLY -> 7
            TimeInterval.MONTHLY -> 30
        }

        return List(points) { index ->
            EnergyDataPoint(
                timestamp = Instant.now().minusSeconds((index * 3600).toLong()),
                generation = Random.nextFloat(),
                consumption = Random.nextFloat(),
            )
        }
    }

    override suspend fun getFaultLogs(): List<FaultLog> {
        return listOf(
            FaultLog(
                id = "1",
                title = "Simulated Warning",
                description = "Mock system alert",
                timestamp = Instant.now(),
                faultCode = "C0123",
                severity = FaultSeverity.WARNING
            )
        )
    }
}
