package com.example.trippocket.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.trippocket.R
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.AccommodationNotification
import com.example.trippocket.data.model.AccommodationNotificationType
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportNotification
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.utils.formatTripTime
import java.time.ZoneId

class NotificationScheduler(
    private val context: Context
) {
    private val alarmManager =
        context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

    fun scheduleTransport(
        notification: TransportNotification,
        transport: Transport
    ) {
        if (!notification.enabled) {
            return
        }

        val notificationTime =
            transport.from.time
                .minusMinutes(notification.minutesBefore)

        val message = buildList {
            transport.from.platform
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    add(
                        context.getString(
                            R.string.notification_transport_platform,
                            it
                        )
                    )
                }
            transport.coach
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    add(
                        context.getString(
                            R.string.notification_transport_coach,
                            it
                        )
                    )
                }
            transport.place
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    add(
                        context.getString(
                            R.string.notification_transport_seat,
                            it
                        )
                    )
                }
        }.takeIf { it.isNotEmpty() }
            ?.joinToString(" • ")

        schedule(
            notificationId = notification.id,
            triggerAtMillis = notificationTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            title = createTransportTitle(
                transport
            ),
            message = message,
            destination = "trip/${transport.tripId}/transport/${transport.id}"
        )
    }

    fun scheduleAccommodation(
        notification: AccommodationNotification,
        accommodation: Accommodation
    ) {
        if (!notification.enabled) {
            return
        }

        val targetTime = when (notification.type) {
            AccommodationNotificationType.CHECK_IN ->
                accommodation.checkIn

            AccommodationNotificationType.CHECK_OUT ->
                accommodation.checkOut
        }

        val notificationTime =
            targetTime.minusMinutes(
                notification.minutesBefore
            )

        schedule(
            notificationId = notification.id,
            triggerAtMillis = notificationTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            title = createAccommodationTitle(
                notification,
                accommodation
            ),
            message = context.getString(
                R.string.notification_accommodation_time,
                formatTripTime(targetTime)
            ),
            destination =
                "trip/${accommodation.tripId}/accommodation/${accommodation.id}"
        )
    }

    fun cancel(
        notificationId: Long
    ) {
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

        alarmManager.cancel(
            pendingIntent
        )

        pendingIntent.cancel()
    }

    private fun schedule(
        notificationId: Long,
        triggerAtMillis: Long,
        title: String,
        message: String?,
        destination: String
    ) {
        if (triggerAtMillis <= System.currentTimeMillis()) {
            return
        }

        val intent = Intent(
            context,
            NotificationReceiver::class.java
        ).apply {
            putExtra(
                "notificationId",
                notificationId
            )

            putExtra(
                "title",
                title
            )

            putExtra(
                "message",
                message
            )

            putExtra(
                "destination",
                destination
            )
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId.toInt(),
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

    private fun createAccommodationTitle(
        notification: AccommodationNotification,
        accommodation: Accommodation
    ): String {
        return when (notification.type) {
            AccommodationNotificationType.CHECK_IN ->
                context.getString(
                    R.string.notification_upcoming_check_in,
                    accommodation.name
                )

            AccommodationNotificationType.CHECK_OUT ->
                context.getString(
                    R.string.notification_upcoming_check_out,
                    accommodation.name
                )
        }
    }

    private fun createTransportTitle(
        transport: Transport
    ): String {
        val transportType = when (
            transport.transportType
        ) {
            TransportType.BUS ->
                context.getString(
                    R.string.transport_type_bus
                )

            TransportType.TRAIN ->
                context.getString(
                    R.string.transport_type_train
                )
        }

        return context.getString(
            R.string.notification_upcoming_transport,
            transportType,
            transport.to.city,
            formatTripTime(transport.from.time)
        )
    }
}