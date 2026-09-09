package com.example.trippocket.ui.trips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.utils.formatTripDateTime

@Composable
fun TransportTicketCard(
    ticket: TransportTicket
) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (ticket.type) {
                        TransportType.BUS -> "🚌 Bus"
                        TransportType.TRAIN -> "🚆 Train"
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "${ticket.from.city} → ${ticket.to.city}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Departure: ${formatTripDateTime(ticket.from.time)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Arrival: ${formatTripDateTime(ticket.to.time)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}