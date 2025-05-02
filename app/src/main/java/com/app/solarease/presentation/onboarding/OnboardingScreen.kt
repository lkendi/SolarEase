package com.app.solarease.presentation.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.solarease.R
import com.app.solarease.presentation.auth.AuthState
import com.app.solarease.presentation.auth.AuthViewModel
import com.app.solarease.presentation.common.components.CustomButton
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.common.theme.SolarYellow
import com.app.solarease.presentation.common.theme.White


@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                Toast.makeText(context, "Signed in successfully", Toast.LENGTH_SHORT).show()
                navController.navigate("home") { popUpTo("onboarding") { inclusive = true } }
            }
            is AuthState.Error -> {
                val msg = (authState as AuthState.Error).message
                errorMessage = when {
                    msg.contains("network", true) -> "Please check your internet connection"
                    msg.contains("timeout", true) -> "Connection timed out. Try again"
                    else -> "Something went wrong. Please try again"
                }
                showError = true
            }
            else -> {}
        }
    }

    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Oops!", color = White) },
            text = { Text(errorMessage, color = White.copy(alpha = 0.8f)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showError = false
                        viewModel.signInWithGoogle(
                            context,
                            context.getString(R.string.default_web_client_id)
                        )
                    }
                ) {
                    Text("Try Again", color = SolarYellow)
                }
            },
            dismissButton = {
                TextButton(onClick = { showError = false }) {
                    Text("Cancel", color = SolarYellow)
                }
            },
            containerColor = Color.DarkGray
        )
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLargeScreen = maxWidth > 800.dp
        val horizontalPadding = if (isLargeScreen) 48.dp else 32.dp
        val imageSize = if (isLargeScreen) 400.dp else 300.dp

        if (isLargeScreen) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = horizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(64.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.solar_monitoring),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(imageSize)
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
                OnboardingContent(
                    onSignInClick = { viewModel.signInWithGoogle(context, context.getString(R.string.default_web_client_id)) },
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.solar_monitoring),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(bottom = 2.dp),
                    contentScale = ContentScale.Fit
                )
                OnboardingContent(
                    onSignInClick = { viewModel.signInWithGoogle(context, context.getString(R.string.default_web_client_id)) },
                    isLoading = isLoading
                )
            }
        }
    }
}

@Composable
fun OnboardingContent(
    onSignInClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.headlineLarge
                        .copy(color = White, fontWeight = FontWeight.SemiBold)
                        .toSpanStyle()
                ) {
                    append("Welcome to ")
                }
                withStyle(
                    style = MaterialTheme.typography.displayLarge
                        .copy(color = SolarYellow)
                        .toSpanStyle()
                ) {
                    append("SolarEase")
                }
            },
            lineHeight = 55.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Effortlessly monitor your solar energy production and consumption from anywhere, anytime.",
            style = MaterialTheme.typography.titleLarge.copy(
                color = White.copy(alpha = 0.9f),
                lineHeight = 28.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(48.dp))
        CustomButton(
            text = if (isLoading) "Please wait..." else "Continue with Google",
            onClick = { if (!isLoading) onSignInClick() },
            modifier = Modifier.fillMaxWidth(),
            icon = {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            backgroundColor = Color.Transparent,
            contentColor = SolarYellow,
            borderColor = SolarYellow
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SolarEaseTheme {
        OnboardingScreen(
            navController = rememberNavController(),
            viewModel = hiltViewModel()
        )
    }
}
