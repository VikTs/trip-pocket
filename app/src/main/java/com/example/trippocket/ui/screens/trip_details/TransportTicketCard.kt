package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.utils.formatTripDateTime
import com.example.trippocket.utils.openTicket

@Composable
fun TransportTicketCard(
    ticket: TransportTicket,
    onClick: (ticketId: Long) -> Unit,
    isFirst: Boolean,
    isLast: Boolean
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimelineNode(
            transportType = ticket.transportType,
            isFirst = isFirst,
            isLast = isLast
        )

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            onClick = { onClick(ticket.id) }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ticket.from.city,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(horizontal = 4.dp)
                        )

                        Text(
                            text = ticket.to.city,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Dep. ${formatTripDateTime(ticket.from.time)}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Arrival: ${formatTripDateTime(ticket.to.time)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                ticket.documentPath?.let { path ->
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(
                                end = 12.dp,
                                top = 4.dp
                            ),
                        shape = CircleShape,
                        color = colors.surfaceVariant,
                        shadowElevation = 2.dp,
                        onClick = {
                            openTicket(
                                context = context,
                                path = path
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "Ticket",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    }
}