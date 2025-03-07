package com.example.solarease.presentation.devices.battery.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.solarease.presentation.common.theme.EnergyOrange
import com.example.solarease.presentation.common.theme.Typography
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt

@Composable
fun BatteryChargingIndicator(
    progress: Float,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "batteryAnimation"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 70f
            val radius = size.toPx() / 2
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            drawArc(
                color = Color.Gray.copy(alpha = 0.3f),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )
            drawArc(
                color = Color(0xFFFFC107),
                startAngle = 135f,
                sweepAngle = 270f * animatedProgress.value,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )
        }
        val innerSize = size * 0.8f
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(innerSize)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFC107), EnergyOrange)
                    ),
                    shape = CircleShape
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Charging",
                    color = Color.White,
                    style = Typography.titleMedium,
                    fontSize = 14.sp
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = Color.White,
                    style = Typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        val boltSize = size * 0.28f
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-boltSize / 36))
                .size(boltSize)
                .background(color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = TablerIcons.Bolt,
                contentDescription = "Bolt",
                modifier = Modifier.size(boltSize * 0.7f),
                tint = Color(0xFFFFC107)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun BatteryChargingIndicatorPreview() {
    BatteryChargingIndicator(progress = 0.85f, size = 150.dp)
}
