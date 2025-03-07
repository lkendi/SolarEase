package com.example.solarease.presentation.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.solarease.domain.model.TimeInterval
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White

@Composable
fun HeaderSection(timeInterval: TimeInterval) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Energy Analytics",
            style = Typography.headlineMedium,
            color = White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
