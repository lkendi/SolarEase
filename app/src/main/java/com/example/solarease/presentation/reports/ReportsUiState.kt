package com.example.solarease.presentation.reports

import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.FaultLog

data class ReportsUiState(
    val energyData: EnergyData? = null,
    val faultLogs: List<FaultLog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
