package com.app.solarease.presentation.reports

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.domain.model.TimeInterval
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.SuccessGreen
import com.app.solarease.presentation.common.theme.Typography
import compose.icons.TablerIcons
import compose.icons.tablericons.Home
import compose.icons.tablericons.Sun
import kotlin.math.abs

@Composable
fun ReportsScreen(
    navController: NavController
) {
    var selectedInterval by remember { mutableStateOf(TimeInterval.DAILY) }
    val intervals = TimeInterval.entries

    Column(
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Reports",
            style = Typography.headlineLarge,
            color = White,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            intervals.forEachIndexed { index, interval ->
                SegmentedButton(
                    onClick = { selectedInterval = interval },
                    selected = selectedInterval == interval,
                    shape = SegmentedButtonDefaults.itemShape(index, intervals.size),
                    label = {
                        Text(
                            interval.name.lowercase().replaceFirstChar { it.titlecase() },
                            color = if (selectedInterval == interval) SolarYellow else White
                        )
                    },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SolarYellow.copy(alpha=0.1f),
                        inactiveContainerColor = Color.Transparent,
                        activeBorderColor = SolarYellow,
                        activeContentColor = SolarYellow,
                    )
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        EnergySummaryCards()

        Spacer(Modifier.height(32.dp))

        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
            EnergyFlowChart(
                title = "Production Trend",
                dataPoints = when (selectedInterval) {
                    TimeInterval.DAILY -> listOf(5f, 8f, 6f, 7f, 5f, 6f, 8f)
                    TimeInterval.WEEKLY -> listOf(5f, 15f, 42f, 18f, 35f, 48f)
                    TimeInterval.MONTHLY -> listOf(120f, 150f, 180f, 200f, 170f, 190f)
                    TimeInterval.YEARLY -> listOf(1000f, 1200f, 1400f, 1600f, 1800f, 2000f)
                },
                color = SolarOrange,
                icon = TablerIcons.Sun
            )

            EnergyFlowChart(
                title = "Consumption Trend",
                dataPoints = when (selectedInterval) {
                    TimeInterval.DAILY -> listOf(3f, 4f, 5f, 6f, 4f, 5f, 4f)
                    TimeInterval.WEEKLY -> listOf(3f, 12f, 28f, 15f, 22f, 31f)
                    TimeInterval.MONTHLY -> listOf(90f, 110f, 130f, 160f, 140f, 150f)
                    TimeInterval.YEARLY -> listOf(800f, 900f, 1000f, 1100f, 1200f, 1300f)
                },
                color = SolarBlue,
                icon = TablerIcons.Home
            )
        }

        Spacer(Modifier.height(32.dp))

    }
}


@Composable
private fun MetricComparisonCard(
    title: String,
    currentValue: String,
    previousValue: String,
    color: Color,
    backgroundColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = Typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = currentValue,
                style = Typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            val trend = currentValue.split(" ")[0].toDouble() - previousValue.split(" ")[0].toDouble()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = if (trend > 0) SuccessGreen.copy(alpha = 0.2f)
                            else ErrorRed,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (trend > 0) Icons.Default.KeyboardArrowUp
                            else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (trend > 0) SuccessGreen
                            else ErrorRed
                        )
                        Text(
                            text = "${"%.1f".format(abs(trend))} kWh",
                            style = Typography.labelMedium,
                            color = if (trend > 0) SuccessGreen
                            else ErrorRed
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EnergyFlowChart(
    title: String,
    dataPoints: List<Float>,
    color: Color,
    icon: ImageVector
) {
    Card(
       colors = CardDefaults.cardColors(containerColor = SolarYellow.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                LineChart(
                    dataPoints = dataPoints,
                    color = color,
                    maxValue = dataPoints.maxOrNull() ?: 1f
                )
            }

            Spacer(Modifier.height(16.dp))

            Legend(
                items = listOf(
                    "Current Week" to color,
                    "Previous Week" to MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
private fun LineChart(
    dataPoints: List<Float>,
    color: Color,
    maxValue: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val spaceBetween = size.width / (dataPoints.size - 1)
        val minValue = dataPoints.minOrNull() ?: 0f

        val maxY = size.height - (maxValue/maxValue * size.height)
        val minY = size.height - (minValue/maxValue * size.height)

        drawLine(
            color = color.copy(alpha = 0.3f),
            start = Offset(0f, maxY),
            end = Offset(size.width, maxY),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )

        drawLine(
            color = color.copy(alpha = 0.3f),
            start = Offset(0f, minY),
            end = Offset(size.width, minY),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )


        val path = Path().apply {
            dataPoints.forEachIndexed { index, value ->
                val x = index * spaceBetween
                val y = size.height - (value/maxValue * size.height)
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }


        drawPath(
            path = path.apply {
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.2f), Color.Transparent)
            )
        )

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3f)
        )


        dataPoints.forEachIndexed { index, value ->
            val x = index * spaceBetween
            val y = size.height - (value/maxValue * size.height)
            drawCircle(
                color = color,
                radius = 4.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}

@Composable
private fun EnergySummaryCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MetricComparisonCard(
            title = "Production",
            currentValue = "42.8 kWh",
            previousValue = "38.4 kWh",
            color = SolarOrange,
            icon = TablerIcons.Sun,
            backgroundColor = SolarOrange.copy(alpha = 0.4f),
            modifier = Modifier.weight(1f)
        )

        MetricComparisonCard(
            title = "Consumption",
            currentValue = "28.3 kWh",
            previousValue = "25.1 kWh",
            color = SolarBlue,
            icon = TablerIcons.Home,
            backgroundColor = SolarBlue.copy(alpha = 0.4f),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Legend(
    items: List<Pair<String, Color>>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { (label, color) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(color)
                )
                Text(
                    text = label,
                    style = Typography.labelMedium,
                    color = White
                )
            }
        }
    }
}

@Preview
@Composable
fun ReportsScreenPreview() {
    SolarEaseTheme {
        ReportsScreen(
            navController = rememberNavController()
        )
    }
}
