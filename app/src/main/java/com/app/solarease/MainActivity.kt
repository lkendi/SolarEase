package com.app.solarease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.solarease.presentation.app.viewmodel.AppViewModel
import com.app.solarease.presentation.app.state.InitState
import com.app.solarease.presentation.app.state.PermissionState
import com.app.solarease.presentation.common.navigation.AppNavigation
import com.app.solarease.presentation.common.navigation.Screen
import com.app.solarease.presentation.common.theme.SolarEaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SolarEaseTheme {
                val vm: AppViewModel = hiltViewModel()
                val initState by vm.initState.collectAsState()
                val permState by vm.permissionState.collectAsState()
                val ready by vm.ready.collectAsState()

                LaunchedEffect(initState, permState, ready) {
                    splash.setKeepOnScreenCondition {
                        initState is InitState.Loading ||
                                (initState is InitState.Authenticated && !ready)
                    }
                }

                val startDestination = when {
                    initState is InitState.Authenticated && permState == PermissionState.Granted && ready ->
                        Screen.Home.route
                    initState is InitState.Authenticated && permState == PermissionState.Denied ->
                        Screen.LocationPermission.route
                    initState is InitState.Authenticated ->
                        Screen.LocationPermission.route
                    else ->
                        Screen.Onboarding.route
                }

                if (initState !is InitState.Loading) {
                    AppNavigation(startDestination = startDestination)
                }
            }
        }
    }
}
