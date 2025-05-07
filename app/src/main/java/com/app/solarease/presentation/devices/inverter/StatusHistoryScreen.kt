package com.app.solarease.presentation.devices.inverter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronDown
import compose.icons.tablericons.ChevronUp
import compose.icons.tablericons.History
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class StatusHistoryItem(
    val timestamp: ZonedDateTime,
    val status: String,
    val powerOutput: Float,
    val details: Map<String, String>
)

@Composable
fun StatusHistoryScreen(
    navController: NavController? = null
) {
    val sampleHistory = listOf(
        StatusHistoryItem(
            timestamp = ZonedDateTime.ofInstant(Instant.now().minusSeconds(60), ZoneId.systemDefault()),
            status = "Active",
            powerOutput = 3.2f,
            details = mapOf("Voltage" to "230V", "Current" to "14A", "Temp." to "45°C")
        ),
        StatusHistoryItem(
            timestamp = ZonedDateTime.ofInstant(Instant.now().minusSeconds(3600), ZoneId.systemDefault()),
            status = "Idle",
            powerOutput = 0.0f,
            details = mapOf("Voltage" to "0V", "Current" to "0A", "Temp." to "30°C")
        ),
        StatusHistoryItem(
            timestamp = ZonedDateTime.ofInstant(Instant.now().minusSeconds(7200), ZoneId.systemDefault()),
            status = "Charging",
            powerOutput = 1.5f,
            details = mapOf("Voltage" to "210V", "Current" to "7A", "Temp." to "40°C")
        )
    )
    StatusHistoryContent(sampleHistory)
}

@Composable
fun StatusHistoryContent(history: List<StatusHistoryItem>) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Full Status History",
            style = Typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(history) { item ->
                StatusHistoryEntry(item)
            }
        }
    }
}

@Composable
fun StatusHistoryEntry(item: StatusHistoryItem) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(tween(300))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = TablerIcons.History,
                contentDescription = null,
                tint = SolarYellow.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = item.timestamp.format(DateTimeFormatter.ofPattern("HH:mm, MMM dd")),
                style = Typography.bodySmall,
                color = SolarYellow.copy(alpha = 0.8f)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${item.status} – ${"%.1f kW".format(item.powerOutput)}",
                style = Typography.bodyMedium,
                color = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = if (expanded) TablerIcons.ChevronUp else TablerIcons.ChevronDown,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 32.dp, bottom = 8.dp)
            ) {
                item.details.forEach { (key, value) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(key, style = Typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                        Text(value, style = Typography.bodySmall, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusHistoryScreenPreview() {
    SolarEaseTheme {
        StatusHistoryScreen()
    }
}
