package com.viktoriia.trippocket.data.model

import java.time.LocalDateTime

sealed interface TripEvent {
    val time: LocalDateTime

    data class TransportItem(
        val transport: Transport
    ) : TripEvent {
        override val time: LocalDateTime
            get() = transport.from.time
    }

    data class AccommodationCheckInItem(
        val accommodation: Accommodation
    ) : TripEvent {
        override val time: LocalDateTime
            get() = accommodation.checkIn
    }

    data class AccommodationCheckOutItem(
        val accommodation: Accommodation
    ) : TripEvent {
        override val time: LocalDateTime
            get() = accommodation.checkOut
    }
}