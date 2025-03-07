package com.example.solarease.presentation.devices.panels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Typography
import com.example.solarease.presentation.common.theme.White
import com.example.solarease.presentation.common.theme.Yellow
import compose.icons.TablerIcons
import compose.icons.tablericons.Sun

@Composable
fun SolarMetric(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Yellow,
            modifier = Modifier.size(24.dp))

        Spacer(Modifier.height(4.dp))

        Text(
            value,
            style = Typography.titleSmall,
            color = White,
            fontWeight = FontWeight.Medium)

        Text(
            label,
            style = Typography.labelSmall,
            color = White.copy(alpha = 0.7f))
    }
}

@Preview
@Composable
fun SolarMetricPreview() {
    SolarEaseTheme {
        SolarMetric(label = "label", value = "value", icon = TablerIcons.Sun )
    }
}
