package com.viktoriia.trippocket.domain.model

import java.time.LocalDate
import java.time.temporal.ChronoUnit

sealed interface TripStatus {
    data class Upcoming(val daysUntil: Long) : TripStatus
    data class Ongoing(val daysRemaining: Long) : TripStatus
    data object Completed : TripStatus
}

fun getTripStatus(
    startDate: LocalDate,
    endDate: LocalDate,
    today: LocalDate = LocalDate.now()
): TripStatus {
    return when {
        today.isBefore(startDate) -> {
            TripStatus.Upcoming(
                daysUntil = ChronoUnit.DAYS.between(today, startDate)
            )
        }

        today.isAfter(endDate) -> TripStatus.Completed

        else -> {
            TripStatus.Ongoing(
                daysRemaining = ChronoUnit.DAYS.between(today, endDate) + 1
            )
        }
    }
}