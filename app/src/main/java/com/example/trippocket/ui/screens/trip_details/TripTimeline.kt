package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.Transport
import com.example.trippocket.utils.formatDayOfWeekDate
import java.time.LocalDateTime

@Composable
fun TripTimeline(
    itemsByDay: Map<java.time.LocalDate, List<TimelineItem>>,
    onTransportClick: (transportId: Long) -> Unit,
    onAccommodationClick: (accommodationId: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 60.dp
        )
    ) {
        itemsByDay.forEach { (date, items) ->

            item {
                Text(
                    text = formatDayOfWeekDate(date),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        vertical = 6.dp
                    )
                )
            }

            itemsIndexed(
                items = items,
                key = { _, item ->
                    when (item) {
                        is TimelineItem.TransportItem ->
                            "transport_${item.transport.id}"

                        is TimelineItem.AccommodationItem ->
                            "accommodation_${item.accommodation.id}"
                    }
                }
            ) { index, item ->

                val today = LocalDateTime.now()

                val isActive =
                    item.time > today

                val previousItem =
                    items.getOrNull(index - 1)

                val isPrevActive =
                    previousItem?.time?.let {
                        it > today
                    } ?: false

                when (item) {

                    is TimelineItem.TransportItem -> {
                        TransportCard(
                            transport = item.transport,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            isActive = isActive,
                            isPrevActive = isPrevActive,
                            onClick = {
                                onTransportClick(
                                    item.transport.id
                                )
                            }
                        )
                    }

                    is TimelineItem.AccommodationItem -> {
                        AccommodationCard(
                            accommodation = item.accommodation,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            isActive = isActive,
                            isPrevActive = isPrevActive,
                            onClick = {
                                onAccommodationClick(
                                    item.accommodation.id
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

sealed interface TimelineItem {
    val time: LocalDateTime

    data class TransportItem(
        val transport: Transport
    ) : TimelineItem {
        override val time: LocalDateTime
            get() = transport.from.time
    }

    data class AccommodationItem(
        val accommodation: Accommodation
    ) : TimelineItem {
        override val time: LocalDateTime
            get() = accommodation.checkIn
    }
}