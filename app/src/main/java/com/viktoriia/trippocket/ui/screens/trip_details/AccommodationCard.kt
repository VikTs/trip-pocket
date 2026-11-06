package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation

@Composable
fun AccommodationCard(
    accommodation: Accommodation,
    isCheckin: Boolean = true,
    onClick: () -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean,
    isNext: Boolean
) {
    val typography = MaterialTheme.typography

    val dateTime = if (isCheckin) {
        accommodation.checkIn
    } else {
        accommodation.checkOut
    }

    val eventLabel = if (isCheckin) {
        R.string.trip_details_accommodation_checkin_label
    } else {
        R.string.trip_details_accommodation_checkout_label
    }

    TimelineEventCard(
        time = dateTime,
        icon = Icons.Default.Hotel,
        onClick = onClick,
        isFirst = isFirst,
        isLast = isLast,
        isActive = isActive,
        isPrevActive = isPrevActive,
        isNext = isNext
    ) {
        Text(
            text = accommodation.name,
            style = typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(eventLabel),
            style = typography.labelLarge
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = accommodation.address,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = typography.bodyMedium
        )
    }
}