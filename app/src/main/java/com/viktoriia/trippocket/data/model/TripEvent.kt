package com.viktoriia.trippocket.data.model

import java.time.LocalDateTime

sealed interface TripEvent {
    val id: Long
    val time: LocalDateTime

    data class TransportItem(
        val transport: Transport
    ) : TripEvent {
        override val id: Long
            get() = transport.id

        override val time: LocalDateTime
            get() = transport.from.time
    }

    data class AccommodationCheckInItem(
        val accommodation: Accommodation
    ) : TripEvent {
        override val id: Long
            get() = accommodation.id

        override val time: LocalDateTime
            get() = accommodation.checkIn
    }

    data class AccommodationCheckOutItem(
        val accommodation: Accommodation
    ) : TripEvent {
        override val id: Long
            get() = accommodation.id

        override val time: LocalDateTime
            get() = accommodation.checkOut
    }
}