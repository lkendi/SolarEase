package com.example.solarease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.solarease.presentation.common.theme.SolarEaseTheme
import com.example.solarease.presentation.navigation.MainScreen
import com.example.solarease.presentation.onboarding.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        var keepSplashVisible = true
        splashScreen.setKeepOnScreenCondition { keepSplashVisible }
        enableEdgeToEdge()
        setContent {
            SolarEaseTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "onboarding_route"
                ) {
                    composable("onboarding_route") {
                        OnboardingScreen(
                            onNavigateToMain = {
                                navController.navigate("main_route") {
                                    popUpTo("onboarding_route") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("main_route") {
                        MainScreen()
                    }
                }
            }
            SideEffect {
                keepSplashVisible = false
            }
        }
    }
}
