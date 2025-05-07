package com.app.solarease.presentation.devices

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.solarease.presentation.common.components.InfoCard
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarBlue
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarOrange
import com.app.solarease.presentation.common.theme.SuccessGreen
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.WarningAmber
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Home
import compose.icons.tablericons.Temperature

@Composable
fun BatteryScreen(
    chargeLevel: Float = 0.82f,
    temperature: Float = 32.4f,
    isCharging: Boolean = false,
    navController: NavController? = null
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Text(
            text = "Battery",
            style = Typography.headlineLarge,
            color = White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            CircularProgressIndicator(
                progress = { chargeLevel },
                modifier = Modifier
                    .size(260.dp)
                    .graphicsLayer {
                        if (isCharging) alpha = pulseAlpha
                    },
                color = when {
                    chargeLevel > 0.75f -> SuccessGreen
                    chargeLevel > 0.25f -> WarningAmber
                    else -> ErrorRed
                },
                strokeWidth = 12.dp,
                trackColor = White.copy(alpha = 0.1f),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (isCharging) TablerIcons.Bolt else TablerIcons.Home,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "${(chargeLevel * 100).toInt()}%",
                    style = Typography.displayMedium,
                    color = White
                )
                Text(
                    text = if (isCharging) "Charging" else "Discharging",
                    style = Typography.titleMedium,
                    color = White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoCard(
                    title = "Health",
                    value = "Good",
                    secondaryValue = "Optimal",
                    icon = TablerIcons.Heart,
                    color = SolarBlue,
                    modifier = Modifier.weight(1f)
                )
                InfoCard(
                    title = "Temperature",
                    value = "${temperature.toInt()}°C",
                    secondaryValue = "Normal",
                    icon = TablerIcons.Temperature,
                    color = SolarOrange,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview
@Composable
fun BatteryScreenPreview() {
    SolarEaseTheme {
        BatteryScreen()
    }
}
