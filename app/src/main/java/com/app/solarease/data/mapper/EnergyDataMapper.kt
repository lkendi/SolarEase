package com.app.solarease.data.mapper

import com.app.solarease.data.model.NetworkDataPoint
import com.app.solarease.data.model.NetworkEnergyData
import com.app.solarease.data.model.NetworkEnergyStats
import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.EnergyDataPoint
import com.app.solarease.domain.model.EnergyStats
import java.time.Instant

fun NetworkEnergyData.toDomainModel() = EnergyData(
    generation = generation.toDomainModel(),
    consumption = consumption.toDomainModel(),
    timeSeries = time_series.map { it.toDomainModel() },
    netBalance = net_balance,
    systemEfficiency = 92.1
)

fun NetworkEnergyStats.toDomainModel() = EnergyStats(
    current = current,
    previous = previous,
    unit = unit
)

fun NetworkDataPoint.toDomainModel() = EnergyDataPoint(
    timestamp = Instant.ofEpochMilli(timestamp).toString(),
    generation = generation,
    consumption = consumption
)
