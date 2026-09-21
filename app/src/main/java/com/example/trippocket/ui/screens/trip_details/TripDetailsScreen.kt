package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.utils.formatDayOfWeekDate
import java.time.LocalDateTime

@Composable
fun TripDetailsScreen(
    trip: Trip,
    transports: List<Transport>,
    onBackClick: () -> Unit,
    onAddTransportClick: () -> Unit,
    onTransportClick: (transportId: Long) -> Unit,
    onEditClick: (tripId: Long) -> Unit,
    onDeleteClick: (tripId: Long) -> Unit
) {
    val colors = MaterialTheme.colorScheme

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
                        onAddTransportClick = onAddTransportClick
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = 60.dp
                        ),
                    ) {
                        val transportsByDay =
                            transports.groupBy { transport -> transport.from.time.toLocalDate() }
                        val firstTransportId = transportsByDay.values.firstOrNull()?.firstOrNull()?.id
                        val lastTransportId = transportsByDay.values.lastOrNull()?.lastOrNull()?.id

                        val today = LocalDateTime.now()
                        var isPrevActive = false

                        transportsByDay.forEach { (date, transports) ->
                            item {
                                Text(
                                    formatDayOfWeekDate(date),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }
                            itemsIndexed(
                                items = transports,
                                key = { _, transport -> transport.id }
                            ) { _, transport ->
                                val isActive = transport.to.time > today

                                TransportCard(
                                    transport = transport,
                                    isFirst = transport.id == firstTransportId,
                                    isLast = transport.id == lastTransportId,
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
                }
                FloatingActionButton(
                    onClick = onAddTransportClick,
                    containerColor = colors.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add transport"
                    )
                }
            }
        }
    }
}