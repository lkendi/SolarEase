package com.app.solarease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import com.app.solarease.presentation.navigation.AppNavigation
import com.app.solarease.presentation.navigation.Screen
import com.app.solarease.presentation.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SolarEaseTheme {
                val initVM: AppViewModel = hiltViewModel()
                val initState by initVM.initState.collectAsState()

                LaunchedEffect(initState) {
                    splash.setKeepOnScreenCondition { initState is AppViewModel.InitState.Loading }
                }

                when (initState) {
                    is AppViewModel.InitState.Authenticated ->
                        AppNavigation(startDestination = Screen.Home.route)
                    is AppViewModel.InitState.Unauthenticated ->
                        AppNavigation(startDestination = Screen.Onboarding.route)
                    is AppViewModel.InitState.Error ->
                        AppNavigation(startDestination = Screen.Onboarding.route)
                    is AppViewModel.InitState.Loading -> Unit
                }
            }
        }
    }
}
