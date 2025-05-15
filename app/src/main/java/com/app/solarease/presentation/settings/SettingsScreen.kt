package com.app.solarease.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.app.solarease.presentation.auth.AuthState
import com.app.solarease.presentation.auth.AuthViewModel
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.Typography
import com.app.solarease.presentation.common.navigation.Screen
import com.app.solarease.presentation.settings.components.SettingsItem
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Logout
import compose.icons.tablericons.User

@Composable
fun SettingsScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val isLoading by authViewModel.isLoading.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val user = (authState as? AuthState.Authenticated)?.user

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Screen.Onboarding.route) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    "Confirm Sign Out",
                    color = SolarYellow,
                    style = Typography.titleLarge
                )
            },
            text = {
                Text(
                    "Are you sure you want to sign out of your account?",
                    color = White.copy(alpha = 0.8f)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.signOut()
                        showLogoutDialog = false
                    }
                ) {
                    Text("Sign Out", color = SolarYellow)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = SolarYellow)
                }
            },
            containerColor = Color.DarkGray
        )
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SolarYellow)
        }
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            style = Typography.headlineLarge,
            color = White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            if (user?.photoUrl.isNullOrEmpty()) {
                Image(
                    imageVector = TablerIcons.User,
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(SolarYellow.copy(alpha = 0.2f))
                )
            } else {
                AsyncImage(
                    model = user?.photoUrl,
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = user?.displayName ?: "Guest User",
                    style = Typography.titleLarge,
                    color = White
                )
                Text(
                    text = user?.email ?: "",
                    style = Typography.bodyMedium,
                    color = White.copy(alpha = 0.8f)
                )
            }
        }

        HorizontalDivider(
            color = SolarYellow.copy(alpha = 0.3f),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsItem(
            title = "Notifications",
            icon = TablerIcons.Bell,
            trailing = {
                Switch(
                    checked = true,
                    onCheckedChange = {}
                )
            }
        )

        SettingsItem(
            title = "Solar System Details",
            icon = TablerIcons.Bolt,
            onClick = { navController.navigate(Screen.SystemDetails.route) }
        )

        SettingsItem(
            title = "Sign Out",
            icon = TablerIcons.Logout,
            onClick = { showLogoutDialog = true }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SolarEaseTheme {
        SettingsScreen(
            navController = rememberNavController()
        )
    }
}
