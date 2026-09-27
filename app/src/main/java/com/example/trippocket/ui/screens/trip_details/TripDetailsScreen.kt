package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.utils.formatDayOfWeekDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    trip: Trip,
    transports: List<Transport>,
    onBackClick: () -> Unit,
    onAddTransportClick: () -> Unit,
    onAddAccommodationClick: () -> Unit,
    onTransportClick: (transportId: Long) -> Unit,
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

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = "Trip details",
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
                if (transports.isEmpty()) {
                    EmptyTripContent(
                        modifier = Modifier.fillMaxSize(),
                        onAddClick = ::openAddSheet
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = 60.dp
                        )
                    ) {
                        val transportsByDay =
                            transports.groupBy {
                                it.from.time.toLocalDate()
                            }

                        val firstTransportId =
                            transportsByDay.values
                                .firstOrNull()
                                ?.firstOrNull()
                                ?.id

                        val lastTransportId =
                            transportsByDay.values
                                .lastOrNull()
                                ?.lastOrNull()
                                ?.id

                        val today = LocalDateTime.now()
                        var isPrevActive = false

                        transportsByDay.forEach { (date, transports) ->
                            item {
                                Text(
                                    formatDayOfWeekDate(date),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(
                                        vertical = 6.dp
                                    )
                                )
                            }

                            itemsIndexed(
                                items = transports,
                                key = { _, transport -> transport.id }
                            ) { _, transport ->

                                val isActive =
                                    transport.to.time > today

                                TransportCard(
                                    transport = transport,
                                    isFirst =
                                        transport.id == firstTransportId,
                                    isLast =
                                        transport.id == lastTransportId,
                                    isActive = isActive,
                                    isPrevActive = isPrevActive,
                                    onClick = {
                                        onTransportClick(transport.id)
                                    }
                                )

                                isPrevActive = isActive
                            }
                        }
                    }
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
        ModalBottomSheet(
            onDismissRequest = {
                showAddSheet = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
            ) {
                Text(
                    text = "Add to trip",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                ListItem(
                    headlineContent = {
                        Text("Transport")
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.DirectionsBus,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable {
                        showAddSheet = false
                        onAddTransportClick()
                    }
                )

                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    headlineContent = {
                        Text("Accommodation")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Hotel,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable {
                        showAddSheet = false
                        onAddAccommodationClick()
                    }
                )
            }
        }
    }
}