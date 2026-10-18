package com.viktoriia.trippocket

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
import com.viktoriia.trippocket.ui.navigation.TripPocketNavHost
import com.viktoriia.trippocket.ui.theme.TripPocketTheme
import com.viktoriia.trippocket.utils.getAppLocale
import com.viktoriia.trippocket.utils.initializeAppLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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