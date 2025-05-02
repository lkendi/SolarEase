package com.app.solarease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.presentation.auth.AuthState
import com.app.solarease.presentation.auth.AuthViewModel
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.navigation.AppNavigation
import com.app.solarease.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        splash.setKeepOnScreenCondition { true }

        setContent {
            SolarEaseTheme {
                val viewModel: AuthViewModel = hiltViewModel()
                val authState by viewModel.authState.collectAsState()

                LaunchedEffect(authState) {
                    if (authState !is AuthState.Checking) {
                        splash.setKeepOnScreenCondition { false }
                    }
                }

                when (authState) {
                    is AuthState.Authenticated -> AppNavigation(startDestination = Screen.Home.route)
                    is AuthState.Unauthenticated -> AppNavigation(startDestination = Screen.Onboarding.route)
                    is AuthState.Error -> AppNavigation(startDestination = Screen.Onboarding.route)
                    is AuthState.Checking -> Unit
                }
            }
        }
    }
}
