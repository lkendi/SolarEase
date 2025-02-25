package com.example.solarease.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.DarkGrey
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Yellow


@Composable
fun IconWithBackground(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    backgroundSize: Dp,
    backgroundColor: Color,
    backgroundColorAlpha: Float,
    modifier: Modifier = Modifier
) {
    val computedIconSize = backgroundSize / 2

    Card(
        shape = CircleShape,
        modifier = modifier
            .size(backgroundSize)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor.copy(alpha = backgroundColorAlpha))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title icon",
                tint = iconColor,
                modifier = Modifier.size(computedIconSize)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun IconWithBackgroundPreview() {
    SolarEaseTheme {
        IconWithBackground(
            icon = Icons.Outlined.Person,
            title = "Person",
            iconColor = Yellow,
            backgroundSize = 50.dp,
            backgroundColor = DarkGrey,
            backgroundColorAlpha = 0.6f
        )
    }
}
