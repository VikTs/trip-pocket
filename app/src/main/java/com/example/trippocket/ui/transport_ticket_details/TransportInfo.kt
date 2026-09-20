package com.example.trippocket.ui.transport_ticket_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.extensions.toDisplayName
import com.example.trippocket.ui.extensions.toIcon

@Composable
fun TransportInfo(transportTicket: TransportTicket) {
    val typography = MaterialTheme.typography

    Row(
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = transportTicket.transportType.toIcon(),
            contentDescription = "Transport type",
            modifier = Modifier
                .padding(top = 2.dp)
                .size(22.dp)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column {
            Text(
                transportTicket.transportNumber ?: transportTicket.transportType.toDisplayName(),
                style = typography.titleLarge
            )

            if (transportTicket.transportType == TransportType.TRAIN) {
                Text(
                    "Coach: ${transportTicket.coach ?: '-'}",
                    style = typography.bodyLarge
                )
            }

            Text(
                "Seat: ${transportTicket.place ?: '-'}",
                style = typography.bodyLarge
            )
        }
    }
}
