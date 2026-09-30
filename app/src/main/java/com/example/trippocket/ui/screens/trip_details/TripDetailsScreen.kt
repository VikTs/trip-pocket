package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    trip: Trip,
    transports: List<Transport>,
    accommodations: List<Accommodation>,
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

    val timelineItems =
        buildList<TimelineItem> {
            transports.forEach {
                add(
                    TimelineItem.TransportItem(it)
                )
            }

            accommodations.forEach {
                add(
                    TimelineItem.AccommodationCheckInItem(it)
                )
            }

            accommodations.forEach {
                add(
                    TimelineItem.AccommodationCheckOutItem(it)
                )
            }
        }.sortedBy {
            it.time
        }

    val itemsByDay =
        timelineItems.groupBy {
            it.time.toLocalDate()
        }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.trip_details_title),
                actions = {
                    TripActionsMenu(
                        tripId = trip.id,
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
            TripDetailsHeader(trip)

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (timelineItems.isEmpty()) {
                    EmptyTripContent(
                        modifier = Modifier.fillMaxSize(),
                        onAddClick = ::openAddSheet
                    )
                } else {
                    TripTimeline(
                        itemsByDay = itemsByDay,
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
