package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarBlue
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                modifier = Modifier.width(100.dp),
                color = SolarBlue.copy(alpha = 0.3f),
                trackColor = Color.Transparent
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Analyzing energy patterns...",
                color = White.copy(alpha = 0.7f),
                style = Typography.bodyMedium
            )
        }
    }
}
