package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import java.time.Instant

@Composable
fun RadiationBar(
    time: String,
    value: Float,
    max: Float,
    height: Dp
) {
    val fillPercentage = (value / max).coerceIn(0f, 1f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(30.dp)
    ) {
        Box(
            modifier = Modifier
                .height(height)
                .width(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Yellow.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(height * fillPercentage)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            0f to Yellow,
                            0.7f to Yellow.copy(alpha = 0.8f),
                            startY = 100f,
                            endY = 0f
                        )
                    )
            )
        }

        Spacer(Modifier.height(8.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "${value.toInt()}W",
            style = Typography.labelSmall,
            color = White.copy(alpha = 0.9f))

        Text(
            time,
            style = Typography.labelSmall,
            color = White.copy(alpha = 0.7f))
        }
    }
}

@Preview
@Composable
fun RadiationBarPreview() {
    SolarEaseTheme {
        RadiationBar(time = Instant.now().toString(), value = 40f, max = 80f , height = 100.dp)
    }
}
