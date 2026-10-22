package com.viktoriia.trippocket.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.viktoriia.trippocket.MainActivity
import com.viktoriia.trippocket.R

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return
        }

        val notificationId =
            intent.getLongExtra(
                "notificationId",
                0L
            )

        val title =
            intent.getStringExtra("title")
                ?: "Trip Pocket"

        val message =
            intent.getStringExtra("message")

        val channelId =
            intent.getStringExtra("channelId")
                ?: NotificationChannel.TRANSPORT

        val destination =
            intent.getStringExtra("destination")

        val openIntent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP

            putExtra(
                "destination",
                destination
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId.toInt(),
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .apply {
                if (!message.isNullOrBlank()) {
                    setContentText(message)
                }
            }
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                notificationId.toInt(),
                notification
            )
    }
}