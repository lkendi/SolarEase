package com.app.solarease.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.solarease.presentation.common.theme.Black
import com.app.solarease.presentation.common.theme.SolarEaseTheme

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Black,
    borderColor: Color = Color.Transparent
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier.padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun CustomButtonPreviewWithIcon() {
    SolarEaseTheme {
        CustomButton(
            text = "Button",
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star Icon"
                )
            },
            backgroundColor = Yellow,
            contentColor = Black,
            borderColor = Color.Transparent
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun CustomButtonPreviewWithoutIcon() {
    SolarEaseTheme {
        CustomButton(
            text = "Button",
            onClick = { },
            borderColor = Yellow,
        )
    }
}
