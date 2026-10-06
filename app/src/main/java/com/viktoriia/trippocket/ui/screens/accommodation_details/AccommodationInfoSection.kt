package com.viktoriia.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.utils.formatAccommodationDateTime
import java.time.temporal.ChronoUnit

@Composable
fun AccommodationInfoSection(
    accommodation: Accommodation,
) {
    val context = LocalContext.current
    val nightsAmount = ChronoUnit.DAYS.between(
        accommodation.checkIn.toLocalDate(),
        accommodation.checkOut.toLocalDate()
    )

    DetailRow(
        label = stringResource(R.string.accommodation_details_check_in_label),
        value = formatAccommodationDateTime(context, accommodation.checkIn)
    )

    Spacer(modifier = Modifier.height(16.dp))

    DetailRow(
        label = stringResource(R.string.accommodation_details_check_out_label),
        value = formatAccommodationDateTime(context, accommodation.checkOut)
    )

    Spacer(modifier = Modifier.height(16.dp))

    DetailRow(
        label = stringResource(R.string.accommodation_details_duration_label),
        value = pluralStringResource(
            R.plurals.accommodation_duration_nights,
            nightsAmount.toInt(),
            nightsAmount
        )
    )
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}