package com.example.trippocket.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.trippocket.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
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