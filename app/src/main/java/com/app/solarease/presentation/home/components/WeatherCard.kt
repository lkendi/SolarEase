package com.app.solarease.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud
import compose.icons.tablericons.CloudRain
import compose.icons.tablericons.Sun

@Composable
fun WeatherCard(
    weatherCondition: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val (gradient, icon) = when(weatherCondition.lowercase()) {
        "rainy" -> Pair(
            Brush.verticalGradient(listOf(Color(0xFF6B8DD6), Color(0xFF4A6AB0))),
            TablerIcons.CloudRain
        )
        "cloudy" -> Pair(
            Brush.verticalGradient(listOf(Color(0xFFB0BEC5), Color(0xFF78909C))),
            TablerIcons.Cloud
        )
        else -> Pair(
            Brush.verticalGradient(listOf(Color(0xFFFFD700), Color(0xFFFFA500))),
            TablerIcons.Sun
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Weather",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = weatherCondition.replaceFirstChar { it.titlecase() },
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherCardPreview(){
    SolarEaseTheme {
        WeatherCard(
            weatherCondition ="Cloudy",
            subtitle = "Peak production"
        )
    }
}
