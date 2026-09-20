package com.example.trippocket.ui.transport_ticket_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType

@Composable
fun TransportInfo(transportTicket: TransportTicket) {
    val typography = MaterialTheme.typography
    val transportIcon = when (transportTicket.transportType) {
        TransportType.BUS -> Icons.Default.DirectionsBus
        TransportType.TRAIN -> Icons.Default.Train
    }

    Row(
        verticalAlignment = Alignment.Top
    ) {
        if (transportTicket.transportNumber != null ||
            transportTicket.place != null
        ) {
            Icon(
                imageVector = transportIcon,
                contentDescription = "Transport type",
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(22.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )
        }

        Column {
            transportTicket.transportNumber?.let {
                Text(
                    it,
                    style = typography.titleLarge
                )
            }

            transportTicket.place?.let {
                Text(
                    "Seat: $it",
                    style = if (transportTicket.transportNumber == null)
                        typography.titleLarge
                    else
                        typography.bodyLarge
                )
            }
        }
    }
}