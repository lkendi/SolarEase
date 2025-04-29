package com.app.solarease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.app.solarease.ui.theme.SolarEaseTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepSplashVisible = true
        splashScreen.setKeepOnScreenCondition { keepSplashVisible }

        lifecycleScope.launch {
            delay(1000)
            keepSplashVisible = false
        }

        enableEdgeToEdge()
        setContent {
            SolarEaseTheme {
            }
        }
    }
}
