package com.example.trippocket

import android.app.Application
import com.example.trippocket.notification.NotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TripPocketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationChannel.create(this)
    }
}