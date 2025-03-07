package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.SuccessGreen


@Composable
fun EnergyMetricCard(
    title: String,
    currentValue: Double,
    previousValue: Double,
    unit: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "%.1f".format(currentValue),
                    style = MaterialTheme.typography.displaySmall,
                    color = color
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            val comparison = currentValue - previousValue
            Text(
                text = "${if (comparison >= 0) "+" else ""}${"%.1f".format(comparison)} $unit",
                style = MaterialTheme.typography.labelMedium,
                color = if (comparison >= 0) SuccessGreen else ErrorRed
            )
        }
    }
}

@Preview
@Composable
fun EnergyMetricCardPreview() {
    SolarEaseTheme {
        EnergyMetricCard(
            title = "Generation",
            currentValue = 100.0,
            previousValue = 90.0,
            unit = "kWh",
            color = Color.Blue
        )
    }
}