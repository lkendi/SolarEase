package com.app.solarease.presentation.onboarding

import android.Manifest
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.presentation.app.viewmodel.AppViewModel
import com.app.solarease.presentation.app.state.PermissionState
import com.app.solarease.presentation.common.components.IconWithBackground
import com.app.solarease.presentation.common.theme.DarkGrey
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.theme.White
import compose.icons.TablerIcons
import compose.icons.tablericons.Location

@Composable
fun LocationPermissionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = hiltViewModel()
) {
    val permissionState by appViewModel.permissionState.collectAsState()
    val showQuitDialog = remember { mutableStateOf(false) }
    val activity = LocalActivity.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        appViewModel.handlePermissionResult(granted)
    }

    LaunchedEffect(permissionState) {
        if (permissionState == PermissionState.Granted) {
            navController.navigate("home") {
                popUpTo("onboarding") { inclusive = true }
            }
        }
    }

    if (showQuitDialog.value) {
        AlertDialog(
            onDismissRequest = { showQuitDialog.value = false },
            title = { Text("Exit App?") },
            text = { Text("Are you sure you want to quit the app?") },
            confirmButton = {
                TextButton(onClick = {
                    showQuitDialog.value = false
                    activity?.finish()
                }) { Text("Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog.value = false }) { Text("No") }
            },
            containerColor = DarkGrey,
            titleContentColor = SolarYellow,
            textContentColor = White
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconWithBackground(
            icon = TablerIcons.Location,
            title = "Location",
            iconColor = DarkGrey,
            backgroundSize = 50.dp,
            backgroundColor = SolarYellow,
            backgroundColorAlpha = 1f
        )
        Spacer(modifier.height(16.dp))

        Text(
            text = "Location Permission",
            style = Typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = SolarYellow
        )
        Text(
            text = "We use your location to improve solar efficiency with weather‑based suggestions and accurate monitoring.",
            style = Typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = White,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showQuitDialog.value = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SolarYellow),
                border = BorderStroke(1.dp, SolarYellow)
            ) { Text("Quit App") }
            Button(
                onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                colors = ButtonDefaults.buttonColors(containerColor = SolarYellow, contentColor = Color.Black)
            ) { Text("Continue") }
        }
    }
}

@Preview
@Composable
fun PreviewLocationPermission() {
    SolarEaseTheme {
        LocationPermissionScreen(navController = rememberNavController())
    }
}
