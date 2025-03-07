package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.devices.panels.WeatherState

@Composable
fun WeatherRecommendations(state: WeatherState) {
    when (state) {
        is WeatherState.Success -> {
            val recs = remember(state.data) {
                listOf(
                    if (state.data.current.solarRadiation > 800) "Optimal sunlight conditions"
                    else "Partial sunlight - check tilt angles",
                    if (state.data.current.precipitation > 5) "Rain expected - panels will self-clean"
                    else "No rain forecast - schedule cleaning",
                    if (state.data.current.cloudCover > 50) "Cloudy conditions - reduced output expected"
                    else "Clear skies - peak production expected"
                )
            }
            Column {
                Text(
                    "Recommendations",
                    style = Typography.titleMedium,
                    color = White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                recs.forEach { rec ->
                    RecommendationItem(text = rec)
                }
            }
        }
        else -> {}
    }
}
