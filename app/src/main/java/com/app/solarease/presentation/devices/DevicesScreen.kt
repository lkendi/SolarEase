package com.app.solarease.presentation.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device
import com.app.solarease.presentation.common.theme.ErrorRed
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.devices.components.DeviceCard

@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier,
    onDeviceClick: (Device) -> Unit = {},
    viewModel: DeviceViewModel = hiltViewModel(),
) {
    val state by viewModel.devicesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDevices()
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            text = "Devices",
            style = Typography.headlineLarge,
            color = White,
            modifier = modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        when (state) {
            is Resource.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SolarYellow)
                }
            is Resource.Error ->
                Text(
                    text = (state as Resource.Error).message,
                    color = ErrorRed,
                    modifier = Modifier.padding(16.dp)
                )
            is Resource.Success -> {
                val devices = (state as Resource.Success<List<Device>>).data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(devices) { d ->
                        DeviceCard(device = d, onClick = { onDeviceClick(d) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview() {
    SolarEaseTheme {
        DevicesScreen()
    }
}
