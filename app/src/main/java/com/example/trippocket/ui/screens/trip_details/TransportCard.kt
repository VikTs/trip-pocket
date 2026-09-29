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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Transport
import com.example.trippocket.ui.extensions.toIcon
import com.example.trippocket.utils.formatTripDateTime
import com.example.trippocket.utils.formatTripTime

@Composable
fun TransportCard(
    transport: Transport,
    onClick: (ticketId: Long) -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean
) {
    val departureTime = transport.from.time
    val arrivalTime = transport.to.time

    val arrivalTimeString =
        if (arrivalTime.toLocalDate() == departureTime.toLocalDate())
            formatTripTime(arrivalTime)
        else formatTripDateTime(arrivalTime)

    val departureInfo = listOfNotNull(
        transport.transportNumber,
        transport.coach?.let { "${stringResource(R.string.trip_details_transport_card_coach_label)}: $it" },
        transport.place?.let { "${stringResource(R.string.trip_details_transport_card_seat_label)}: $it" }
    ).joinToString(", ")


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(formatTripTime(departureTime), style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.width(8.dp))

        TimelineNode(
            icon = transport.transportType.toIcon(),
            isFirst = isFirst,
            isLast = isLast,
            isActive = isActive,
            isPrevActive = isPrevActive
        )

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            onClick = { onClick(transport.id) },
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
                            text = transport.from.city,
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
                            text = transport.to.city,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (departureInfo.isNotBlank()) {
                        Text(
                            text = "${stringResource(R.string.trip_details_transport_card_department_label)}: $departureInfo",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = "${stringResource(R.string.trip_details_transport_card_arrival_label)}: $arrivalTimeString",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}