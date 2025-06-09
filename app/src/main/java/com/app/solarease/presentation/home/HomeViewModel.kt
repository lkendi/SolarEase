package com.app.solarease.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.IoTData
import com.app.solarease.domain.usecase.iotdata.GetIoTDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

data class EnergyMetrics(
    val batteryLevel: String,
    val batteryProgress: Float,
    val batteryRemaining: String,
    val homeUsage: String,
    val homeUsageProgress: Float,
    val costSavings: String,
    val costSavingsSecondary: String,
    val co2Reduced: String,
    val co2ReducedSecondary: String
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIoTDataUseCase: GetIoTDataUseCase,
    @Named("deviceId") private val deviceId: String
) : ViewModel() {

    private val _energyMetrics = mutableStateOf<Resource<EnergyMetrics>?>(null)
    val energyMetrics: State<Resource<EnergyMetrics>?> = _energyMetrics

    init {
        fetchLatestIoTData()
    }

    fun refresh() {
        fetchLatestIoTData()
    }

    private fun fetchLatestIoTData() {
        viewModelScope.launch {
            _energyMetrics.value = Resource.Loading()
            when (val result = getIoTDataUseCase(deviceId)) {
                is Resource.Success -> {
                    _energyMetrics.value = Resource.Success(processIoTData(result.data))
                }
                is Resource.Error -> {
                    _energyMetrics.value = Resource.Error(result.message)
                }
                is Resource.Loading -> {
                    _energyMetrics.value = Resource.Loading()
                }
            }
        }
    }

    private fun processIoTData(data: IoTData): EnergyMetrics {
        val minBatteryVoltage = 0.0
        val maxBatteryVoltage = 4.2
        val batteryProgress = ((data.batteryVoltage - minBatteryVoltage) / (maxBatteryVoltage - minBatteryVoltage)).toFloat().coerceIn(0f, 1f)
        val batteryLevel = "${(batteryProgress * 100).toInt()}%"

        val batteryCapacityAh = 4.8
        val remainingAh = batteryCapacityAh * batteryProgress
        val remainingHours = if (data.loadCurrent > 0) (remainingAh / data.loadCurrent).toInt() else 0
        val batteryRemaining = "$remainingHours h remaining"

        val homeUsageW = data.batteryVoltage * data.loadCurrent
        val homeUsage = "%.2f W".format(homeUsageW)
        val maxLoadW = 1.0
        val homeUsageProgress = (homeUsageW / maxLoadW).toFloat().coerceIn(0f, 1f)

        val assumedSolarCurrent = 0.5
        val solarPowerW = data.solarVoltage * assumedSolarCurrent
        val solarEnergyKWh = solarPowerW / 1000.0 * 24.0
        val costPerKWh = 20.0
        val costSavings = "%.2f Ksh".format(solarEnergyKWh * costPerKWh)
        val costSavingsSecondary = "0% more than last month"

        val co2PerKWh = 0.5
        val co2ReducedKg = solarEnergyKWh * co2PerKWh
        val co2Reduced = "%.2f kg".format(co2ReducedKg)
        val co2ReducedSecondary = "Equivalent to %.1f trees".format(co2ReducedKg / 10.0)

        return EnergyMetrics(
            batteryLevel = batteryLevel,
            batteryProgress = batteryProgress,
            batteryRemaining = batteryRemaining,
            homeUsage = homeUsage,
            homeUsageProgress = homeUsageProgress,
            costSavings = costSavings,
            costSavingsSecondary = costSavingsSecondary,
            co2Reduced = co2Reduced,
            co2ReducedSecondary = co2ReducedSecondary
        )
    }
}
