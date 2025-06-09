package com.app.solarease.data.repository

import com.app.solarease.common.Resource
import com.app.solarease.data.model.IoTDataDto
import com.app.solarease.data.remote.IoTDataApiService
import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.EnergyDataPoint
import com.app.solarease.domain.model.EnergyStats
import com.app.solarease.domain.model.TimeInterval
import com.app.solarease.domain.repository.EnergyRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class EnergyRepositoryImpl @Inject constructor(
    private val apiService: IoTDataApiService,
    private val deviceId: String
) : EnergyRepository {

    override suspend fun getEnergyData(interval: TimeInterval): Resource<EnergyData> {
        return try {
            val now = Instant.now()
            val startTime = when (interval) {
                TimeInterval.DAILY -> now.minus(24, ChronoUnit.HOURS)
                TimeInterval.WEEKLY -> now.minus(7, ChronoUnit.DAYS)
                TimeInterval.MONTHLY -> now.minus(30, ChronoUnit.DAYS)
                TimeInterval.YEARLY -> now.minus(365, ChronoUnit.DAYS)
            }

            val iotDataList = apiService.getIoTData(deviceId)
                .filter { Instant.parse(it.timestamp).isAfter(startTime) }
                .sortedBy { it.timestamp }

            val timeSeries = processIoTData(iotDataList, interval)

            val totalGeneration = timeSeries.sumOf { it.generation }
            val totalConsumption = timeSeries.sumOf { it.consumption }

            Resource.Success(
                EnergyData(
                    generation = EnergyStats(
                        current = totalGeneration,
                        previous = totalGeneration * 0.9,
                        unit = "Wh"
                    ),
                    consumption = EnergyStats(
                        current = totalConsumption,
                        previous = totalConsumption * 0.9,
                        unit = "Wh"
                    ),
                    timeSeries = timeSeries,
                    netBalance = totalGeneration - totalConsumption,
                    systemEfficiency = 92.1
                )
            )
        } catch (e: Exception) {
            Resource.Error("Failed to fetch energy data: ${e.message}")
        }
    }

    private fun processIoTData(data: List<IoTDataDto>, interval: TimeInterval): List<EnergyDataPoint> {
        if (data.isEmpty()) return emptyList()

        val pointsCount = when (interval) {
            TimeInterval.DAILY -> 7
            TimeInterval.WEEKLY -> 6
            TimeInterval.MONTHLY -> 6
            TimeInterval.YEARLY -> 6
        }

        val firstTime = Instant.parse(data.first().timestamp)
        val lastTime = Instant.parse(data.last().timestamp)
        val totalSeconds = ChronoUnit.SECONDS.between(firstTime, lastTime).toDouble()
        val stepSeconds = totalSeconds / (pointsCount - 1)

        val buckets = MutableList(pointsCount) { 0.0 to 0.0 }
        val assumedSolarCurrent = 0.1
        data.forEach { iotData ->
            val time = Instant.parse(iotData.timestamp)
            val secondsSinceStart = ChronoUnit.SECONDS.between(firstTime, time).toDouble()
            val bucketIndex = ((secondsSinceStart / stepSeconds).toInt()).coerceIn(0, pointsCount - 1)

            val productionPower = iotData.solarVoltage * assumedSolarCurrent
            val consumptionPower = iotData.batteryVoltage * iotData.loadCurrent
            val timeIntervalHours = stepSeconds / 3600.0
            val generationEnergy = productionPower * timeIntervalHours
            val consumptionEnergy = consumptionPower * timeIntervalHours
            buckets[bucketIndex] = (buckets[bucketIndex].first + generationEnergy) to
                    (buckets[bucketIndex].second + consumptionEnergy)
        }

        return buckets.mapIndexed { index, (gen, cons) ->
            EnergyDataPoint(
                timestamp = firstTime.plus((index * stepSeconds).toLong(), ChronoUnit.SECONDS).toString(),
                generation = gen,
                consumption = cons
            )
        }
    }
}
