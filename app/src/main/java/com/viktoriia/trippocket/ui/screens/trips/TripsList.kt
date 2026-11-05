package com.viktoriia.trippocket.ui.screens.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.viewmodel.TripsViewModel
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

@Composable
fun TripsList(
    tripsByYear: Map<Int, List<Trip>>,
    showYears: Boolean,
    onTripClick: (Trip) -> Unit,
    onNextEventClick: (Long, TripEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activeTrips = tripsByYear.values
        .flatten()
        .filter {
            val status = getTripStatus(it.startDate, it.endDate)
            status is TripStatus.Ongoing
        }

    val viewModel: TripsViewModel = hiltViewModel()

    val activeTripEventsFlow = remember(activeTrips) {
        viewModel.getTripEvents(activeTrips.map { it.id })
            .map { tripEvents ->
                tripEvents.mapNotNull { (tripId, events) ->
                    val nextEvent = events.firstOrNull {
                        it.time > LocalDateTime.now()
                    }

                    nextEvent?.let { tripId to it }
                }.toMap()
            }
    }

    val activeTripEvents by activeTripEventsFlow.collectAsStateWithLifecycle(
        initialValue = emptyMap()
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (tripsByYear.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.trips_toggle_empty_message),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        } else {
            tripsByYear.forEach { (year, yearTrips) ->
                if (showYears) {
                    item {
                        Text(
                            text = year.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                items(yearTrips) { trip ->
                    TripCard(
                        trip = trip,
                        onClick = { onTripClick(trip) },
                        onNextEventClick = onNextEventClick,
                        isActive = activeTrips.any { it.id == trip.id },
                        nextEvent = activeTripEvents[trip.id]
                    )
                }
            }
        }
    }
}