package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.ui.utils.formatDayOfWeekDate
import java.time.LocalDateTime

@Composable
fun TripTimeline(
    itemsByDay: Map<java.time.LocalDate, List<TripEvent>>,
    nextEvent: TripEvent?,
    onTransportClick: (transportId: Long) -> Unit,
    onAccommodationClick: (accommodationId: Long) -> Unit
) {
    val context = LocalContext.current

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
                    text = formatDayOfWeekDate(context, date),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(
                        vertical = 6.dp
                    )
                )
            }

            itemsIndexed(
                items = items,
                key = { _, item -> "${item::class.simpleName}_${item.id}" }
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
                    is TripEvent.TransportItem -> {
                        TransportCard(
                            transport = item.transport,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            isActive = isActive,
                            isPrevActive = isPrevActive,
                            isNext = (nextEvent?.id == item.id)
                                    && (nextEvent is TripEvent.TransportItem),
                            onClick = {
                                onTransportClick(
                                    item.id
                                )
                            }
                        )
                    }

                    is TripEvent.AccommodationCheckInItem -> {
                        AccommodationCard(
                            accommodation = item.accommodation,
                            isCheckin = true,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            isActive = isActive,
                            isPrevActive = isPrevActive,
                            isNext = (nextEvent?.id == item.id)
                                    && (nextEvent is TripEvent.AccommodationCheckInItem),
                            onClick = {
                                onAccommodationClick(
                                    item.id
                                )
                            }
                        )
                    }

                    is TripEvent.AccommodationCheckOutItem -> {
                        AccommodationCard(
                            accommodation = item.accommodation,
                            isCheckin = false,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            isActive = isActive,
                            isPrevActive = isPrevActive,
                            isNext = (nextEvent?.id == item.id)
                                    && (nextEvent is TripEvent.AccommodationCheckOutItem),
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