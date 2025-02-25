package com.example.solarease.presentation.onboarding.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow

@Composable
fun HeaderText() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = Typography.displayLarge.toSpanStyle().copy(color = White)) {
                append("Welcome to")
            }
            withStyle(style = Typography.displayLarge.toSpanStyle().copy(color = Yellow)) {
                append(" SolarEase")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        lineHeight = 50.sp
    )
}

@Composable
fun DescriptionText() {
    Text(
        text = "Effortlessly monitor your solar energy,\nAnywhere, Anytime!",
        style = MaterialTheme.typography.headlineMedium.copy(color = White, fontWeight = FontWeight.SemiBold),
        modifier = Modifier.fillMaxWidth(),
        lineHeight = 32.sp
    )
}