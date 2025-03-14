package com.example.solarease.presentation.onboarding

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.solarease.R
import com.example.solarease.presentation.common.components.CustomButton
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.common.theme.Yellow
import com.example.solarease.presentation.navigation.NavigationEvent
import com.example.solarease.presentation.onboarding.components.DescriptionText
import com.example.solarease.presentation.onboarding.components.HeaderText



@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToHome -> {
                    navController.navigate("home_route") {
                        popUpTo("onboarding_route") { inclusive = true }
                    }
                }
            }
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isLargeScreen = maxWidth > 800.dp
        val buttonWidthFraction = if (isLargeScreen) 0.4f else 0.6f
        if (isLargeScreen) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.solar_monitoring),
                    contentDescription = "Monitoring Illustration",
                    modifier = modifier.padding(end = 10.dp)
                )
                OnboardingContent(
                    buttonWidthFraction = buttonWidthFraction,
                    onGetStarted = { viewModel.onGetStartedClicked() }
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.solar_monitoring),
                    contentDescription = "Monitoring Illustration",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                )
                OnboardingContent(
                    buttonWidthFraction = buttonWidthFraction,
                    onGetStarted = { viewModel.onGetStartedClicked() }
                )
            }
        }
    }
}

@Composable
fun OnboardingContent(
    buttonWidthFraction: Float,
    onGetStarted: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        HeaderText()
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionText()
        Spacer(modifier = Modifier.height(22.dp))
        CustomButton(
            text = "Get Started",
            onClick = onGetStarted,
            backgroundColor = Color.Transparent,
            borderColor = Yellow,
            contentColor = Yellow
        )
    }
}


@Preview
@Composable
fun OnboardingScreenPreview(){
    SolarEaseTheme {
        OnboardingScreen(navController = NavHostController(LocalContext.current))
    }
}