package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography

@Composable
fun TimeIntervalSelector(
    selectedInterval: TimeInterval,
    onIntervalSelected: (TimeInterval) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeInterval.entries.forEach { interval ->
            val selected = interval == selectedInterval
            Text(
                text = interval.name.lowercase().replaceFirstChar { it.titlecase() },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onIntervalSelected(interval) }
                    .background(
                        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                    .padding(horizontal = 30.dp, vertical = 8.dp),
                color = if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                style = Typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun TimeIntervalSelectorPreview() {
    SolarEaseTheme {
        TimeIntervalSelector(
            selectedInterval = TimeInterval.WEEKLY,
            onIntervalSelected = {}
        )
    }
}
