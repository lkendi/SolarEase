package com.app.solarease.presentation.devices.inverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.domain.model.FaultLog
import com.app.solarease.domain.model.FaultSeverity
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import java.time.Instant
import java.time.ZonedDateTime

@Composable
fun InverterScreen(
    navController: NavController,
    statusHistory: List<StatusHistoryItem> = sampleStatusHistory(),
    faultLogs: List<FaultLog> = sampleFaultLogs()
) {
    LazyColumn(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Inverter Status",
                style = Typography.headlineLarge,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            StatusHistorySection(statusHistory, navController)
        }
        item {
            FaultLogsSection(faultLogs, navController)
        }
    }
}

@Composable
private fun StatusHistorySection(
    history: List<StatusHistoryItem>,
    navController: NavController?
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarBlue.copy(alpha = 0.1f)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Status History",
                    style = Typography.titleMedium,
                    color = Color.White
                )
                TextButton(onClick = { navController?.navigate("status_history") }) {
                    Text(
                        text = "See More",
                        style = Typography.bodySmall,
                        color = SolarYellow
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            history.take(3).forEach { item ->
                StatusHistoryEntry(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun FaultLogsSection(
    logs: List<FaultLog>,
    navController: NavController?
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SolarYellow.copy(alpha = 0.1f)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Faults",
                    style = Typography.titleMedium,
                    color = Color.White
                )
                TextButton(onClick = { navController?.navigate("faults") }) {
                    Text(
                        text = "See More",
                        style = Typography.bodySmall,
                        color = SolarYellow
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            logs.take(3).forEach { log ->
                FaultLogItem(log)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InverterScreenPreview() {
    SolarEaseTheme {
        InverterScreen( navController = rememberNavController())
    }
}

private fun sampleStatusHistory() = listOf(
    StatusHistoryItem(
        timestamp = ZonedDateTime.now().minusHours(1),
        status = "Optimal",
        powerOutput = 4.1f,
        details = emptyMap()
    )
)

private fun sampleFaultLogs() = listOf(
    FaultLog(
        timestamp = Instant.now(),
        errorCode = "ERR-045",
        description = "Over temperature protection",
        id = "123",
        title = "Over temp",
        severity = FaultSeverity.INFO
    )
)
