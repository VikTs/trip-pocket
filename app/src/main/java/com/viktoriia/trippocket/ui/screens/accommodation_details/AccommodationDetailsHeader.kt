package com.viktoriia.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.ui.components.AddressLink
import com.viktoriia.trippocket.ui.components.PhoneLink

@Composable
fun AccommodationDetailsHeader(
    accommodation: Accommodation,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Hotel,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(35.dp)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column {
            Text(
                text = accommodation.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            AddressLink(
                address = accommodation.address,
                additionalInfo = accommodation.name
            )
        }
    }

    accommodation.contactPhone?.let {
        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Contact phone",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(35.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = stringResource(R.string.accommodation_details_contact_number_label),
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                PhoneLink(
                    phone = it,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}