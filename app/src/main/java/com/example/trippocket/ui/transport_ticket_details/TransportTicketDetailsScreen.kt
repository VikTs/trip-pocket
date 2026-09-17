package com.example.trippocket.ui.transport_ticket_details


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.ui.trip_details.TripActionsMenu
import com.example.trippocket.utils.openTicket

@Composable
fun TransportTicketDetailsScreen(
    transportTicket: TransportTicket,
    onBackClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    onEditClick: (id: Long) -> Unit,
) {
    val context = LocalContext.current
    val transportIcon = when (transportTicket.transportType) {
        TransportType.BUS -> Icons.Default.DirectionsBus
        TransportType.TRAIN -> Icons.Default.Train
    }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = "Transport details",
                actions = {
                    TransportTicketActionsMenu(
                        transportTicketId = transportTicket.id,
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                },

                )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            transportTicket.transportNumber?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = transportIcon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(it, style = MaterialTheme.typography.titleLarge)
                }
            }

            transportTicket.transportNumber?.let {
                Spacer(modifier = Modifier.height(4.dp))
            }

            transportTicket.place?.let { place ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(26.dp))

                    Text(
                        "Seat: $place",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TransportTimeline(
                ticket = transportTicket
            )

            Spacer(modifier = Modifier.height(24.dp))

            transportTicket.documentPath?.let {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = {
                        openTicket(
                            context = context,
                            path = it
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Open ticket")
                }

            }

        }
    }
}