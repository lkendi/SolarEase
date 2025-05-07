package com.app.solarease.presentation.devices.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.solarease.domain.model.CurrentWeather
import com.app.solarease.domain.model.HourlyWeather
import com.app.solarease.domain.model.Weather
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.Sun
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SolarIntensityCard(w: Weather) {
    val data = w.hourly.map { it.radiation.toFloat() }
    val maxRadiation = data.maxOrNull()?.coerceAtLeast(1f) ?: 1f
    val yTicks = listOf(0f, maxRadiation/2, maxRadiation)

    var selectedIndex by remember { mutableIntStateOf(-1) }
    val barWidthDp = 48.dp
    val gapDp = 8.dp
    val totalWidth = barWidthDp * data.size + gapDp * (data.size - 1)

    Card(
        colors = CardDefaults.cardColors(SolarYellow.copy(alpha = .1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Hourly Solar Intensity", style = Typography.titleMedium, color = SolarYellow)
            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .height(240.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Canvas(
                    modifier = Modifier
                        .height(240.dp)
                        .width(totalWidth)
                        .pointerInput(data) {
                            detectTapGestures { offset ->
                                val barW = barWidthDp.toPx()
                                val gap = gapDp.toPx()
                                val idx = (offset.x / (barW + gap)).toInt().coerceIn(0, data.lastIndex)
                                selectedIndex = if (selectedIndex == idx) -1 else idx
                            }
                        }
                ) {
                    val yAxisPaint = Paint().apply {
                        color = White.copy(alpha = 0.2f).toArgb()
                        strokeWidth = 1.dp.toPx()
                    }

                    yTicks.forEach { value ->
                        val yPos = size.height - (value / maxRadiation * size.height)
                        drawContext.canvas.nativeCanvas.drawLine(
                            0f,
                            yPos,
                            size.width,
                            yPos,
                            yAxisPaint
                        )
                    }

                    val textPaint = Paint().apply {
                        color = White.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = Paint.Align.LEFT
                    }

                    yTicks.forEach { value ->
                        val yPos = size.height - (value / maxRadiation * size.height)
                        drawContext.canvas.nativeCanvas.drawText(
                            "${value.toInt()} W/m²",
                            8.dp.toPx(),
                            yPos - 4.dp.toPx(),
                            textPaint
                        )
                    }

                    val barW = barWidthDp.toPx()
                    val gap = gapDp.toPx()

                    data.forEachIndexed { i, v ->
                        val h = (v / maxRadiation) * size.height
                        val x = i * (barW + gap)
                        val isSelected = i == selectedIndex

                        drawRoundRect(
                            color = SolarYellow.copy(alpha = 0.2f),
                            topLeft = Offset(x, size.height - h),
                            size = Size(barW, h),
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )

                        drawRoundRect(
                            color = if (isSelected) SolarBlue else SolarYellow,
                            topLeft = Offset(x, size.height - h),
                            size = Size(barW, h),
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )

                        val time = w.hourly[i].time.format(DateTimeFormatter.ofPattern("HH:mm"))
                        drawContext.canvas.nativeCanvas.drawText(
                            time,
                            x + barW/2,
                            size.height - 8.dp.toPx(),
                            Paint().apply {
                                color = White.toArgb()
                                textAlign = Paint.Align.CENTER
                                textSize = 12.sp.toPx()
                            }
                        )

                        if (isSelected) {
                            drawRoundRect(
                                color = SolarBlue,
                                topLeft = Offset(x + barW/2 - 40.dp.toPx(), size.height - h - 48.dp.toPx()),
                                size = Size(80.dp.toPx(), 32.dp.toPx()),
                                cornerRadius = CornerRadius(8.dp.toPx())
                            )

                            drawContext.canvas.nativeCanvas.drawText(
                                "${v.toInt()} W/m²",
                                x + barW/2,
                                size.height - h - 32.dp.toPx(),
                                Paint().apply {
                                    color = White.toArgb()
                                    textAlign = Paint.Align.CENTER
                                    textSize = 14.sp.toPx()
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SolarIntensityCardPreview() {
    val now = ZonedDateTime.now()
    val sampleHourly = (0 until 12).map {
        HourlyWeather(
            time = now.plusHours(it.toLong()),
            radiation = (200..800).random().toDouble(),
            cloudCover = 0.0,
            precipitationProb = 0.0
        )
    }
    val sampleWeather = Weather(
        current = CurrentWeather(
            time = now,
            weatherCode = 0,
            description = "Sunny",
            icon = TablerIcons.Sun
        ),
        hourly = sampleHourly,
        daily = emptyList()
    )

    SolarEaseTheme {
        SolarIntensityCard(w = sampleWeather)
    }
}
