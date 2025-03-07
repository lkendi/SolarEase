package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.FaultLog
import com.example.solarease.domain.model.FaultSeverity
import com.example.solarease.presentation.common.theme.EcoGreen
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Yellow
import com.example.solarease.util.formatDateTime
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.X
import java.time.Instant

@Composable
fun FaultLogItem(log: FaultLog) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Handle click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    when (log.severity) {
                        FaultSeverity.CRITICAL -> ErrorRed.copy(alpha = 0.2f)
                        FaultSeverity.WARNING -> Yellow.copy(alpha = 0.2f)
                        else -> EcoGreen.copy(alpha = 0.2f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (log.severity) {
                    FaultSeverity.CRITICAL -> TablerIcons.X
                    FaultSeverity.WARNING -> Icons.Default.Warning
                    else -> Icons.Default.Info
                },
                contentDescription = "Severity",
                tint = when (log.severity) {
                    FaultSeverity.CRITICAL -> ErrorRed
                    FaultSeverity.WARNING -> Yellow
                    else -> EcoGreen
                },
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = log.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = log.timestamp.formatDateTime(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = TablerIcons.ChevronRight,
            contentDescription = "View details",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun FaultLogItemPreview() {
    SolarEaseTheme {
        FaultLogItem(log = FaultLog(
            id = "F0",
            title = "System Failure",
            severity = FaultSeverity.CRITICAL,
            description = "Failure of the system",
            faultCode = "C0123",
            timestamp = Instant.now()
        ))
    }
}
