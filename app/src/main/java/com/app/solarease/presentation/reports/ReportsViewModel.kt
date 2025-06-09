package com.app.solarease.presentation.reports

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.EnergyData
import com.app.solarease.domain.model.TimeInterval
import com.app.solarease.domain.usecase.energydata.GetEnergyDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getEnergyDataUseCase: GetEnergyDataUseCase
) : ViewModel() {

    private val _selectedInterval = mutableStateOf(TimeInterval.DAILY)
    val selectedInterval: State<TimeInterval> = _selectedInterval

    private val _energyData = mutableStateOf<Resource<EnergyData>?>(null)
    val energyData: State<Resource<EnergyData>?> = _energyData

    init {
        fetchEnergyData(TimeInterval.DAILY)
    }

    fun onIntervalSelected(interval: TimeInterval) {
        _selectedInterval.value = interval
        fetchEnergyData(interval)
    }

    private fun fetchEnergyData(interval: TimeInterval) {
        viewModelScope.launch {
            _energyData.value = Resource.Loading()
            _energyData.value = getEnergyDataUseCase(interval)
        }
    }
}
