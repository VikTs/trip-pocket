package com.viktoriia.trippocket

import android.app.Application
import com.viktoriia.trippocket.notification.NotificationChannel
import com.viktoriia.trippocket.notification.NotificationRescheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class TripPocketApplication : Application() {
    @Inject lateinit var rescheduler: NotificationRescheduler

    override fun onCreate() {
        super.onCreate()
        NotificationChannel.create(this)

        CoroutineScope(Dispatchers.IO).launch {
            rescheduler.rescheduleAll()
        }
    }
}