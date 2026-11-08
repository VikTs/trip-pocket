package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.ui.components.ItemEditMenu
import com.viktoriia.trippocket.ui.components.TopBar
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    trip: Trip,
    tripEvents: List<TripEvent>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onAddTransportClick: () -> Unit,
    onAddAccommodationClick: () -> Unit,
    onTransportClick: (transportId: Long) -> Unit,
    onAccommodationClick: (accommodationId: Long) -> Unit,
    onEditClick: (tripId: Long) -> Unit,
    onDeleteClick: (tripId: Long) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    var showAddSheet by rememberSaveable {
        mutableStateOf(false)
    }

    fun openAddSheet() {
        showAddSheet = true
    }
    val today = LocalDate.now()

    val tripDays = remember(tripEvents) {
        tripEvents
            .map { it.time.toLocalDate() }
            .distinct()
            .sorted()
    }

    val isTripActive = getTripStatus(
        trip.startDate,
        trip.endDate
    ) is TripStatus.Ongoing

    val defaultSelectedDay = remember(tripDays, isTripActive, today) {
        if (!isTripActive) {
            null
        } else {
            tripDays
                .firstOrNull { it == today }
                ?.toString()
                ?: tripDays
                    .firstOrNull { it.isAfter(today) }
                    ?.toString()
        }
    }

    var selectedDayOverride by rememberSaveable(trip.id) {
        mutableStateOf<String?>(null)
    }

    var hasSelectedDayOverride by rememberSaveable(trip.id) {
        mutableStateOf(false)
    }

    val selectedDay = if (hasSelectedDayOverride) {
        selectedDayOverride
    } else {
        defaultSelectedDay
    }

    val filteredEvents = remember(tripEvents, selectedDay) {
        selectedDay?.let { day ->
            tripEvents.filter {
                it.time.toLocalDate().toString() == day
            }
        } ?: tripEvents
    }

    val itemsByDay = remember(filteredEvents) {
        filteredEvents.groupBy {
            it.time.toLocalDate()
        }
    }

    val nextEvent = remember(tripEvents) {
        tripEvents
            .filter { it.time > LocalDateTime.now() }
            .minByOrNull { it.time }
    }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.trip_details_title),
                actions = {
                    ItemEditMenu(
                        itemId = trip.id,
                        itemDeletionTitle = stringResource(R.string.trip_details_delete_confirmation_title),
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
        ) {
            TripDetailsHeader(
                trip = trip
            )

            if (!isLoading && tripEvents.size > 1) {
                TripDayFilters(
                    days = tripDays,
                    selectedDay = selectedDay,
                    onDaySelected = { day ->
                        selectedDayOverride = day
                        hasSelectedDayOverride = true
                    }
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (tripEvents.isEmpty()) {
                    EmptyTripContent(
                        modifier = Modifier.fillMaxSize(),
                        onAddClick = ::openAddSheet
                    )
                } else {
                    TripTimeline(
                        itemsByDay = itemsByDay,
                        nextEvent = nextEvent,
                        onTransportClick = onTransportClick,
                        onAccommodationClick = onAccommodationClick
                    )

                    FloatingActionButton(
                        onClick = ::openAddSheet,
                        containerColor = colors.primary,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddToTripBottomSheet(
            onDismiss = {
                showAddSheet = false
            },
            onAddTransportClick = {
                showAddSheet = false
                onAddTransportClick()
            },
            onAddAccommodationClick = {
                showAddSheet = false
                onAddAccommodationClick()
            }
        )
    }
}
