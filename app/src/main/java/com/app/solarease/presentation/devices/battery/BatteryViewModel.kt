package com.app.solarease.presentation.devices.battery

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

data class BatteryMetrics(
    val chargeLevel: Float,
    val isCharging: Boolean = false,
    val temperature: Double,
    val temperatureStatus: String,
    val healthValue: String,
    val healthSecondary: String
)

@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val getIoTDataUseCase: GetIoTDataUseCase,
    @Named("deviceId") private val deviceId: String
) : ViewModel() {

    private val _batteryMetrics = mutableStateOf<Resource<BatteryMetrics>?>(null)
    val batteryMetrics: State<Resource<BatteryMetrics>?> = _batteryMetrics

    init {
        fetchLatestIoTData()
    }

    fun refresh() {
        fetchLatestIoTData()
    }

    private fun fetchLatestIoTData() {
        viewModelScope.launch {
            _batteryMetrics.value = Resource.Loading()
            when (val result = getIoTDataUseCase(deviceId)) {
                is Resource.Success -> {
                    _batteryMetrics.value = Resource.Success(processIoTData(result.data))
                }
                is Resource.Error -> {
                    _batteryMetrics.value = Resource.Error(result.message)
                }
                is Resource.Loading -> {
                    _batteryMetrics.value = Resource.Loading()
                }
            }
        }
    }

    private fun processIoTData(data: IoTData): BatteryMetrics {
        val minBatteryVoltage = 0.0
        val maxBatteryVoltage = 4.2
        val chargeLevel = ((data.batteryVoltage - minBatteryVoltage) / (maxBatteryVoltage - minBatteryVoltage)).toFloat().coerceIn(0f, 1f)
        val temperatureStatus = if (data.batteryTemperature < 40) "Normal" else "High"
        return BatteryMetrics(
            chargeLevel = chargeLevel,
            temperature = data.batteryTemperature,
            temperatureStatus = temperatureStatus,
            healthValue = "Good",
            healthSecondary = "Optimal"
        )
    }
}
