package com.example.trippocket.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.trippocket.data.model.Notification
import com.example.trippocket.data.model.Transport
import com.example.trippocket.ui.extensions.toDisplayName
import com.example.trippocket.utils.formatTripTime
import java.time.ZoneId

class NotificationScheduler(
    private val context: Context
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(
        notification: Notification,
        transport: Transport
    ) {
        if (!notification.enabled) {
            return
        }

        val notificationTime = transport.from.time
            .minusMinutes(notification.minutesBefore)

        val triggerAtMillis = notificationTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        if (triggerAtMillis <= System.currentTimeMillis()) {
            return
        }

        val intent = Intent(
            context,
            NotificationReceiver::class.java
        ).apply {
            putExtra("notificationId", notification.id)
            putExtra(
                "title",
                "Upcoming ${transport.transportType.toDisplayName().lowercase()} to ${transport.to.city}"
            )
            putExtra("message", "Departure time: ${formatTripTime(transport.from.time)}")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    fun cancel(notificationId: Long) {
        val intent = Intent(
            context,
            NotificationReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}