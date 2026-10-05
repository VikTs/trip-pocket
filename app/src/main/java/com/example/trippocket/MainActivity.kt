package com.example.trippocket

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.trippocket.ui.navigation.TripPocketNavHost
import com.example.trippocket.ui.theme.TripPocketTheme
import com.example.trippocket.utils.getAppLocale
import com.example.trippocket.utils.initializeAppLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

    override fun attachBaseContext(
        newBase: Context
    ) {
        val locale = getAppLocale(newBase)

        val configuration =
            Configuration(newBase.resources.configuration)

        configuration.setLocale(locale)

        val context =
            newBase.createConfigurationContext(
                configuration
            )

        super.attachBaseContext(context)
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        initializeAppLanguage(this)

        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }

        val destination =
            intent.getStringExtra("destination")

        setContent {
            TripPocketTheme {
                TripPocketNavHost(
                    destination = destination
                )
            }
        }
    }
}