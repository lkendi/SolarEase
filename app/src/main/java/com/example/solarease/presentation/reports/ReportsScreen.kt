package com.example.solarease.presentation.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.solarease.domain.model.EnergyData
import com.example.solarease.domain.model.EnergyDataPoint
import com.example.solarease.domain.model.EnergyStats
import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.model.FaultSeverity
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.reports.components.EmptyState
import com.example.solarease.presentation.reports.components.EnergyAreaCharts
import com.example.solarease.presentation.reports.components.ErrorState
import com.example.solarease.presentation.reports.components.FaultLogsSection
import com.example.solarease.presentation.reports.components.FullScreenLoading
import com.example.solarease.presentation.reports.components.HeaderSection
import com.example.solarease.presentation.reports.components.MetricsCards
import com.example.solarease.presentation.reports.components.PerformanceLegend
import com.example.solarease.presentation.reports.components.TimeIntervalSelector
import java.time.Instant
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    when {
        uiState.isLoading -> FullScreenLoading()
        uiState.error != null -> ErrorState(uiState.error!!)
        uiState.energyData != null -> ReportsContent(
            energyData = uiState.energyData!!,
            faultLogs = uiState.faultLogs,
            timeInterval = viewModel.timeInterval.value,
            onIntervalSelected = viewModel::selectTimeInterval
        )
        else -> ErrorState("No data available")
    }
}

@Composable
private fun ReportsContent(
    energyData: EnergyData,
    faultLogs: List<FaultLog>,
    timeInterval: TimeInterval,
    onIntervalSelected: (TimeInterval) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        HeaderSection(timeInterval)

        TimeIntervalSelector(
            selectedInterval = timeInterval,
            onIntervalSelected = onIntervalSelected,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        MetricsCards(energyData)

        Spacer(Modifier.height(32.dp))

        if (energyData.timeSeries.isNotEmpty()) {
            EnergyAreaCharts(
                dataPoints = energyData.timeSeries,
                timeInterval = timeInterval
            )
            PerformanceLegend()
            Spacer(Modifier.height(32.dp))
        } else {
            EmptyState()
        }

        FaultLogsSection(faultLogs)
    }
}


@Preview
@Composable
fun ReportsScreenPreview() {
    SolarEaseTheme {
        ReportsContent(
            energyData = EnergyData(
                generation = EnergyStats(150.5, 140.2, "kWh"),
                consumption = EnergyStats(120.3, 115.8, "kWh"),
                timeSeries = List(24) { index ->
                    EnergyDataPoint(
                        timestamp = Instant.now().minusSeconds((24 - index) * 3600L),
                        generation = (sin(index / 2.0) * 50 + 50).toFloat(),
                        consumption = (cos(index / 2.0) * 30 + 40).toFloat()
                    )
                },
                netBalance = 30.2
            ),
            faultLogs = listOf(
                FaultLog(
                    id = "1",
                    title = "Inverter Efficiency Drop",
                    description = "Efficiency below 80%",
                    timestamp = Instant.now(),
                    faultCode = "PERF-002",
                    severity = FaultSeverity.WARNING
                )
            ),
            timeInterval = TimeInterval.DAILY,
            onIntervalSelected = {}
        )
    }
}

