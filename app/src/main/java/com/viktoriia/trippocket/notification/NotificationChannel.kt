package com.viktoriia.trippocket.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannel {
    const val TRANSPORT = "transport_notifications"
    const val ACCOMMODATION = "accommodation_notifications"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        val transportChannel = NotificationChannel(
            TRANSPORT,
            "Transport",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications about upcoming transport"
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 300, 200, 300)
        }

        val accommodationChannel = NotificationChannel(
            ACCOMMODATION,
            "Accommodation",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications about hotel check-in and check-out"
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 300, 200, 300)
        }

        notificationManager.createNotificationChannel(
            transportChannel
        )

        notificationManager.createNotificationChannel(
            accommodationChannel
        )
    }
}