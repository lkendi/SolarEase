package com.example.solarease.presentation.reports

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.domain.repository.EnergyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: EnergyRepository
) : ViewModel() {
    private val _uiState = mutableStateOf(ReportsUiState())
    val uiState: State<ReportsUiState> = _uiState

    private val _timeInterval = mutableStateOf(TimeInterval.DAILY)
    val timeInterval: State<TimeInterval> = _timeInterval

    fun selectTimeInterval(interval: TimeInterval) {
        _timeInterval.value = interval
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                energyData = null
            )

            try {
                val energyData = repository.getEnergyData(timeInterval.value)
                val faultLogs = repository.getFaultLogs()

                _uiState.value = _uiState.value.copy(
                    energyData = energyData,
                    faultLogs = faultLogs,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error loading data: ${e.localizedMessage}",
                    isLoading = false
                )
            }
        }
    }
}
