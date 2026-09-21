package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.utils.formatTripDates

@Composable
fun TripDetailsScreen(
    trip: Trip,
    tickets: List<TransportTicket>,
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
            Text(
                text = trip.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = formatTripDates(
                    startDate = trip.startDate,
                    endDate = trip.endDate
                ),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (tickets.isEmpty()) {
                    EmptyTripContent(
                        modifier = Modifier.fillMaxSize(),
                        onAddTransportClick = onAddTransportClick
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 24.dp,
                            bottom = 88.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = tickets,
                            key = { _, ticket -> ticket.id }
                        ) { index, ticket ->

                            TransportTicketCard(
                                ticket = ticket,
                                isFirst = index == 0,
                                isLast = index == tickets.lastIndex,
                                onClick = {
                                    onTransportClick(ticket.id)
                                }
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = onAddTransportClick,
                        containerColor = colors.primary,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
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
}