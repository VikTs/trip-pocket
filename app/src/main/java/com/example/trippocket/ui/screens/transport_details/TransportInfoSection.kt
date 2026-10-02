package com.example.trippocket.ui.screens.transport_details

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.trippocket.R
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.PhoneLink
import com.example.trippocket.ui.extensions.toDisplayName
import com.example.trippocket.ui.extensions.toIcon

@Composable
fun TransportInfoSection(transport: Transport) {
    val typography = MaterialTheme.typography

    Row(
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = transport.transportType.toIcon(),
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
                transport.transportNumber ?: transport.transportType.toDisplayName(),
                style = typography.titleLarge
            )

            if (transport.transportType == TransportType.TRAIN) {
                Text(
                    "${stringResource(R.string.transport_details_carrier_label)}: ${transport.carrier ?: '-'}",
                    style = typography.bodyLarge
                )
            }

            Text(
                "${stringResource(R.string.transport_details_seat_label)}: ${transport.place ?: '-'}",
                style = typography.bodyLarge
            )
        }
    }

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    if (transport.transportType == TransportType.BUS &&
        (transport.carrier != null || transport.driverPhone != null)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Carrier",
                modifier = Modifier
                    .size(22.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {
                Text(
                    "${stringResource(R.string.transport_details_carrier_label)}: ${transport.carrier ?: '-'}",
                    style = typography.bodyLarge
                )

                PhoneLink(
                    label = stringResource(
                        R.string.transport_details_driver_phone_label
                    ),
                    phone = transport.driverPhone
                )
            }
        }
    }
}
