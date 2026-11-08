package com.viktoriia.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.ui.components.PhoneButton
import com.viktoriia.trippocket.ui.utils.formatDateTime
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
        value = formatDateTime(context, accommodation.checkIn),
    )

    Spacer(modifier = Modifier.height(28.dp))

    DetailRow(
        label = stringResource(R.string.accommodation_details_check_out_label),
        value = formatDateTime(context, accommodation.checkOut),
    )

    Spacer(modifier = Modifier.height(28.dp))

    DetailRow(
        label = stringResource(R.string.accommodation_details_duration_label),
        value = pluralStringResource(
            R.plurals.accommodation_duration_nights,
            nightsAmount.toInt(),
            nightsAmount
        )
    )

    Spacer(
        modifier = Modifier.height(18.dp)
    )

    accommodation.contactPhone?.let {
        Spacer(
            modifier = Modifier.height(12.dp)
        )

        PhoneButton(
            phone = it,
            label = stringResource(R.string.accommodation_details_call_label)
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(115.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall
        )
    }
}