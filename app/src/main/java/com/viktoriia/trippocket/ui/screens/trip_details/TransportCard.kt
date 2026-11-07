package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Transport
import com.viktoriia.trippocket.ui.extensions.toIcon

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransportCard(
    transport: Transport,
    onClick: (ticketId: Long) -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean,
    isNext: Boolean
) {
    val typography = MaterialTheme.typography

    val transportInfo = listOfNotNull(
        transport.coach?.let {
            "${stringResource(R.string.trip_details_transport_card_coach_label)}: $it"
        },
        transport.place?.let {
            "${stringResource(R.string.trip_details_transport_card_seat_label)}: $it"
        }
    ).joinToString(", ")

    val departureInfo = buildString {
        if (transport.from.code != null) append(transport.from.code)
        else append(transport.from.address)

        transport.from.terminal
            ?.takeIf { it.isNotBlank() }
            ?.let {
                append(", ${stringResource(R.string.trip_details_terminal_label)} $it")
            }
    }

    TimelineEventCard(
        time = transport.from.time,
        icon = transport.transportType.toIcon(),
        onClick = { onClick(transport.id) },
        isFirst = isFirst,
        isLast = isLast,
        isActive = isActive,
        isPrevActive = isPrevActive,
        isNext = isNext
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = transport.from.city,
                style = typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(horizontal = 4.dp)
                )

                Text(
                    text = transport.to.city,
                    style = typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${stringResource(R.string.trip_details_departure_label)}: $departureInfo",
            maxLines = if (transportInfo.isNotBlank()) 1 else 2,
            overflow = TextOverflow.Ellipsis,
            style = typography.bodyMedium
        )

        if (transportInfo.isNotBlank()) {
            Text(
                text = transportInfo,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
        }
    }
}