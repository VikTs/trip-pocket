package com.viktoriia.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.TransportStop
import com.viktoriia.trippocket.data.model.TransportType
import com.viktoriia.trippocket.ui.components.TruncatedAddressLink

@Composable
fun TransportLocation(
    transportType: TransportType,
    location: TransportStop,
    modifier: Modifier = Modifier
) {
    when (transportType) {
        TransportType.PLANE -> PlaneLocation(
            location = location, modifier = modifier
        )

        else -> GroundTransportLocation(
            location = location, modifier = modifier
        )
    }
}

@Composable
fun PlaneLocation(
    location: TransportStop,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = location.city,
                style = MaterialTheme.typography.titleLarge,
                maxLines = if (location.terminal != null) 1 else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            location.code?.let {
                Text(
                    text = " (${it.take(6)})",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        TruncatedAddressLink(
            address = location.address,
            additionalInfo = listOfNotNull(
                location.terminal?.let { "T$it" },
                location.city,
            ).joinToString(","),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        location.terminal?.let {
            Text(
                text =
                    "${stringResource(R.string.transport_details_terminal_label)}: $it",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun GroundTransportLocation(
    location: TransportStop, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = location.city,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(4.dp))

        TruncatedAddressLink(
            address = location.address,
            additionalInfo = location.city,
            maxLines = 1
        )
    }
}