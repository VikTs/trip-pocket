package com.viktoriia.trippocket.data.utils

import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.data.model.Transport
import com.viktoriia.trippocket.data.model.TripEvent

object TripEventFactory {
    fun create(
        transports: List<Transport>,
        accommodations: List<Accommodation>
    ): List<TripEvent> {
        return buildList {
            transports.forEach {
                add(TripEvent.TransportItem(it))
            }

            accommodations.forEach {
                add(TripEvent.AccommodationCheckInItem(it))
                add(TripEvent.AccommodationCheckOutItem(it))
            }
        }.sortedBy { it.time }
    }
}