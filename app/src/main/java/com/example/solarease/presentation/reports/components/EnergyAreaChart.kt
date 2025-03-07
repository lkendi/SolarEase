package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.EcoGreen
import com.example.solarease.presentation.common.theme.ErrorRed
import com.example.solarease.presentation.common.theme.Typography


@Composable
fun EnergyAreaChart(
    title: String,
    dataPoints: List<Float>,
    maxValue: Float,
    timeInterval: TimeInterval,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGrey.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val optimalY = size.height * 0.2f
                    val warningY = size.height * 0.5f


                    drawContext.canvas.nativeCanvas.apply {

                        val optimalPaint = android.graphics.Paint().apply {
                            style = android.graphics.Paint.Style.STROKE
                            strokeWidth = 1.dp.toPx()
                            pathEffect = android.graphics.DashPathEffect(
                                floatArrayOf(8.dp.toPx(), 4.dp.toPx()), 0f
                            )
                        }
                        optimalPaint.setColor(EcoGreen.toArgb())

                        val warningPaint = android.graphics.Paint().apply {
                            style = android.graphics.Paint.Style.STROKE
                            strokeWidth = 1.dp.toPx()
                            pathEffect = android.graphics.DashPathEffect(
                                floatArrayOf(8.dp.toPx(), 4.dp.toPx()), 0f
                            )
                        }
                        warningPaint.setColor(ErrorRed.toArgb())

                        drawLine(0f, optimalY, size.width, optimalY, optimalPaint)
                        drawLine(0f, warningY, size.width, warningY, warningPaint)
                    }

                    val path = Path().apply {
                        moveTo(0f, size.height)
                        dataPoints.forEachIndexed { index, value ->
                            val x = size.width * index.toFloat() / (dataPoints.size - 1)
                            val y = size.height - (value / maxValue * size.height)
                            if (index == 0) moveTo(x, y) else lineTo(x, y)
                        }
                        lineTo(size.width, size.height)
                        close()
                    }

                    drawPath(
                        path = path,
                        brush = Brush.verticalGradient(
                            colors = listOf(color.copy(alpha = 0.3f), Color.Transparent)
                        )
                    )

                    val linePath = Path().apply {
                        dataPoints.forEachIndexed { index, value ->
                            val x = size.width * index.toFloat() / (dataPoints.size - 1)
                            val y = size.height - (value / maxValue * size.height)
                            if (index == 0) moveTo(x, y) else lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = linePath,
                        color = color,
                        style = Stroke(2.dp.toPx())
                    )

                    drawContext.canvas.nativeCanvas.apply {
                        val labelPaint = android.graphics.Paint().apply {
                            textSize = 12.sp.toPx()
                            textAlign = android.graphics.Paint.Align.RIGHT
                        }
                        labelPaint.setColor(EcoGreen.toArgb())
                        drawText(
                            "80% Optimal",
                            size.width - 8.dp.toPx(),
                            optimalY - 8.dp.toPx(),
                            labelPaint
                        )
                        labelPaint.setColor(ErrorRed.toArgb())
                        drawText(
                            "50% Warning",
                            size.width - 8.dp.toPx(),
                            warningY - 8.dp.toPx(),
                            labelPaint
                        )
                    }
                }

            }
        }
    }
}
