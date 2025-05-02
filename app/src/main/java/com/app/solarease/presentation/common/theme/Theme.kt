package com.app.solarease.presentation.common.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val DarkColorScheme = darkColorScheme(
    primary = SolarYellow,
    onPrimary = Black,
    primaryContainer = SolarOrange,
    onPrimaryContainer = Black,
    secondary = SuccessGreen,
    onSecondary = White,
    secondaryContainer = LightCream,
    onSecondaryContainer = Black,
    background = BackgroundGrey,
    onBackground = White,
    surface = DarkGrey,
    onSurface = White,
    surfaceVariant = InactiveGrey,
    onSurfaceVariant = Black,
    error = ErrorRed,
    onError = White,
    outline = InactiveGrey
)

@Composable
fun SolarEaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            content()
        }
    }
}
