package com.app.solarease.presentation.devices.inverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.solarease.domain.model.FaultLog
import com.app.solarease.domain.model.FaultSeverity
import com.app.solarease.presentation.common.components.IconWithBackground
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.utils.formatDateTime
import java.time.Instant

@Composable
fun FaultLogsScreen(
    navController: NavController? = null
) {
    val logs = listOf(
        FaultLog(
            id = "1",
            title = "Overvoltage",
            errorCode = "E101",
            description = "Overvoltage detected",
            severity = FaultSeverity.CRITICAL,
            timestamp = Instant.now().minusSeconds(60)
        ),
        FaultLog(
            id = "2",
            title = "High Battery Temp",
            errorCode = "E205",
            description = "Battery temperature high",
            severity = FaultSeverity.WARNING,
            timestamp = Instant.now().minusSeconds(120)
        ),
        FaultLog(
            id = "3",
            title = "Comm Lost",
            errorCode = "E310",
            description = "Inverter communication lost",
            severity = FaultSeverity.CRITICAL,
            timestamp = Instant.now().minusSeconds(3600)
        )
    )

    FaultLogsContent(logs = logs)
}

@Composable
fun FaultLogsContent(logs: List<FaultLog>) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Fault Log History",
            style = Typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(logs) { log ->
                FaultLogItem(log)
            }
        }
    }
}

@Composable
fun FaultLogItem(log: FaultLog) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconWithBackground(
            icon = Icons.Filled.Warning,
            iconColor = SolarYellow,
            title = "Warning",
            backgroundSize = 50.dp,
            backgroundColor = SolarYellow,
            backgroundColorAlpha = 0.3f
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = log.timestamp.formatDateTime(),
                style = Typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = "${log.errorCode}: ${log.description}",
                style = Typography.bodyMedium,
                color = Color.White
            )
        }
    }

    HorizontalDivider(
        color = SolarYellow.copy(alpha = 0.3f),
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun FaultLogsContentPreview() {
    val sampleLogs = listOf(
        FaultLog(
            id = "1",
            title = "Overvoltage",
            errorCode = "E101",
            description = "Overvoltage detected",
            severity = FaultSeverity.CRITICAL,
            timestamp = Instant.now().minusSeconds(60)
        ),
        FaultLog(
            id = "2",
            title = "High Battery Temp",
            errorCode = "E205",
            description = "Battery temperature high",
            severity = FaultSeverity.WARNING,
            timestamp = Instant.now().minusSeconds(120)
        ),
        FaultLog(
            id = "3",
            title = "Comm Lost",
            errorCode = "E310",
            description = "Inverter communication lost",
            severity = FaultSeverity.CRITICAL,
            timestamp = Instant.now().minusSeconds(3600)
        )
    )

    SolarEaseTheme {
        FaultLogsScreen()
    }
}
