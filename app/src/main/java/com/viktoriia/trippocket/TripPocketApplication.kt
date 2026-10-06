package com.viktoriia.trippocket

import android.app.Application
import com.viktoriia.trippocket.notification.NotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TripPocketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationChannel.create(this)
    }
}