package com.example.trippocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trippocket.ui.navigation.TripPocketNavHost
import com.example.trippocket.ui.theme.TripPocketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripPocketTheme() {
                TripPocketNavHost()
            }
        }
    }
}
