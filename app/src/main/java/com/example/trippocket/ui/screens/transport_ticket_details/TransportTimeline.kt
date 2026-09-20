package com.example.trippocket.ui.screens.transport_ticket_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import java.time.format.DateTimeFormatter

private val TimeFormatter = DateTimeFormatter.ofPattern("H:mm")

@Composable
fun TransportTimeline(
    ticket: TransportTicket,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ticket.from.time.format(TimeFormatter),
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(48.dp)
                    .background(
                        MaterialTheme.colorScheme.outlineVariant
                    )
            )

            Text(
                text = ticket.to.time.format(TimeFormatter),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.height(103.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = ticket.from.city,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = ticket.from.address ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column {
                Text(
                    text = ticket.to.city,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = ticket.to.address ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
