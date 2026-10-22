package com.viktoriia.trippocket.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.data.model.AccommodationNotification
import com.viktoriia.trippocket.data.model.AccommodationNotificationType
import com.viktoriia.trippocket.data.model.Transport
import com.viktoriia.trippocket.data.model.TransportNotification
import com.viktoriia.trippocket.data.model.TransportType
import com.viktoriia.trippocket.utils.formatTripTime
import com.viktoriia.trippocket.utils.getAppLocale
import java.time.ZoneId

class NotificationScheduler(
    private val context: Context
) {
    private val alarmManager =
        context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

    private val textContext: Context
        get() {
            val configuration = Configuration(context.resources.configuration)
            configuration.setLocale(getAppLocale(context))
            return context.createConfigurationContext(configuration)
        }

    private fun transportKey(id: Long): Long = id

    private fun accommodationKey(id: Long): Long =
        ACCOMMODATION_KEY_OFFSET + id

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

        val message = textContext.getString(
            R.string.notification_departure_time,
            formatTripTime(transport.from.time)
        )

        schedule(
            notificationId = transportKey(notification.id),
            triggerAtMillis = notificationTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            title = createTransportTitle(
                transport
            ),
            message = message,
            channelId = NotificationChannel.TRANSPORT,
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
            notificationId = accommodationKey(notification.id),
            triggerAtMillis = notificationTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            title = createAccommodationTitle(
                notification,
                accommodation
            ),
            message = textContext.getString(
                R.string.notification_accommodation_time,
                formatTripTime(targetTime)
            ),
            channelId = NotificationChannel.ACCOMMODATION,
            destination =
                "trip/${accommodation.tripId}/accommodation/${accommodation.id}"
        )
    }

    fun cancelTransport(notificationId: Long) =
        cancelByKey(transportKey(notificationId))

    fun cancelAccommodation(notificationId: Long) =
        cancelByKey(accommodationKey(notificationId))

    fun cancelLegacy(notificationId: Long) =
        cancelByKey(notificationId)

    private fun cancelByKey(
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
        channelId: String,
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
                "channelId",
                channelId
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

        if (
            Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
            alarmManager.canScheduleExactAlarms()
        ) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }

    private fun createAccommodationTitle(
        notification: AccommodationNotification,
        accommodation: Accommodation
    ): String {
        return when (notification.type) {
            AccommodationNotificationType.CHECK_IN ->
                textContext.getString(
                    R.string.notification_upcoming_check_in,
                    accommodation.name
                )

            AccommodationNotificationType.CHECK_OUT ->
                textContext.getString(
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
                textContext.getString(
                    R.string.transport_type_bus
                )

            TransportType.TRAIN ->
                textContext.getString(
                    R.string.transport_type_train
                )
        }

        return textContext.getString(
            R.string.notification_upcoming_transport,
            transportType,
            transport.to.city
        )
    }

    private companion object {
        const val ACCOMMODATION_KEY_OFFSET = 1_000_000_000L
    }
}