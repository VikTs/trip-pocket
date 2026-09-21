package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.background
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
import com.example.trippocket.utils.formatTripTime
import com.example.trippocket.utils.openFile
import java.time.LocalDateTime

@Composable
fun TransportTicketCard(
    ticket: TransportTicket,
    onClick: (ticketId: Long) -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean
) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    val today = LocalDateTime.now()
    val departureTime = ticket.from.time
    val arrivalTime = ticket.to.time

    val arrivalTimeString =
        if (arrivalTime.toLocalDate() == departureTime.toLocalDate())
            formatTripTime(arrivalTime)
        else formatTripDateTime(arrivalTime)

    val departureInfo = listOfNotNull(
        ticket.transportNumber,
        ticket.coach?.let { "coach: $it" },
        ticket.place?.let { "seat: $it" }
    ).joinToString(", ")


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(formatTripTime(departureTime), style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.width(8.dp))

        TimelineNode(
            transportType = ticket.transportType,
            isFirst = isFirst,
            isLast = isLast,
            isActive = isActive,
            isPrevActive = isPrevActive
        )

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            onClick = { onClick(ticket.id) },
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
                        if (
                            isActive &&
                            !isPrevActive &&
                            (!isFirst || departureTime <= today.plusDays(1))
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                        }

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

                    if (departureInfo.isNotBlank()) {
                        Text(
                            text = "Dep.: $departureInfo",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = "Arrival: $arrivalTimeString",
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
                        color = colorScheme.surfaceVariant,
                        shadowElevation = 2.dp,
                        onClick = {
                            openFile(
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