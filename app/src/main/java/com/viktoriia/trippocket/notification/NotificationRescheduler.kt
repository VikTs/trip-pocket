package com.viktoriia.trippocket.notification

import android.content.Context
import com.viktoriia.trippocket.data.repository.AccommodationNotificationRepository
import com.viktoriia.trippocket.data.repository.AccommodationRepository
import com.viktoriia.trippocket.data.repository.TransportNotificationRepository
import com.viktoriia.trippocket.data.repository.TransportRepository
import com.viktoriia.trippocket.data.repository.TripRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRescheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tripRepository: TripRepository,
    private val transportRepository: TransportRepository,
    private val transportNotificationRepository: TransportNotificationRepository,
    private val accommodationRepository: AccommodationRepository,
    private val accommodationNotificationRepository: AccommodationNotificationRepository
) {
    private val scheduler = NotificationScheduler(context)

    suspend fun rescheduleAll() {
        cleanupLegacyAlarmsOnce()

        tripRepository.getTrips().first().forEach { trip ->
            transportRepository.getTripTransports(trip.id).first()
                .forEach { transport ->
                    transportNotificationRepository
                        .getByTransportId(transport.id)
                        .first()
                        ?.let { scheduler.scheduleTransport(it, transport) }
                }

            accommodationRepository.getForTrip(trip.id).first()
                .forEach { accommodation ->
                    accommodationNotificationRepository
                        .getByAccommodationId(accommodation.id)
                        .first()
                        .orEmpty()
                        .forEach { scheduler.scheduleAccommodation(it, accommodation) }
                }
        }
    }

    private suspend fun cleanupLegacyAlarmsOnce() {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        if (prefs.getBoolean(KEY_LEGACY_CLEANED, false)) {
            return
        }

        tripRepository.getTrips().first().forEach { trip ->
            accommodationRepository.getForTrip(trip.id).first()
                .forEach { accommodation ->
                    accommodationNotificationRepository
                        .getByAccommodationId(accommodation.id)
                        .first()
                        .orEmpty()
                        .forEach { scheduler.cancelLegacy(it.id) }
                }
        }

        prefs.edit().putBoolean(KEY_LEGACY_CLEANED, true).apply()
    }

    private companion object {
        const val PREFS_NAME = "notification_migration"
        const val KEY_LEGACY_CLEANED = "legacy_alarms_cleaned_v1"
    }
}