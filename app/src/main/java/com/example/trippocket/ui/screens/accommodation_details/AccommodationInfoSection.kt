package com.example.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.ui.components.AddressLink
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMMM, HH:mm", Locale.ENGLISH)

@Composable
fun AccommodationInfoSection(
    accommodation: Accommodation,
) {
    DetailRow(
        label = stringResource(R.string.accommodation_details_address_label),
    ) {
        AddressLink(
            address = accommodation.address,
            additionalInfo = accommodation.name
        )
    }

    Spacer(modifier = Modifier.height(16.dp))


    DetailRow(
        label = stringResource(R.string.accommodation_details_check_in_label),
        value = accommodation.checkIn.format(dateTimeFormatter)
    )

    Spacer(modifier = Modifier.height(16.dp))


    DetailRow(
        label = stringResource(R.string.accommodation_details_check_out_label),
        value = accommodation.checkOut.format(dateTimeFormatter)
    )
}