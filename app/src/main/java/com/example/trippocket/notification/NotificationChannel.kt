package com.example.trippocket.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannel {
    const val TRANSPORT = "transport_notifications"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val channel = NotificationChannel(
            TRANSPORT,
            "Transport",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications about upcoming transport"
        }

        val notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}