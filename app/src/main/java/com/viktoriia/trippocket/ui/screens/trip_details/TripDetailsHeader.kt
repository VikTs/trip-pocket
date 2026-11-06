package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.ui.utils.formatTripDatesWithYear

@Composable
fun TripDetailsHeader(
    trip: Trip,
) {
    val context = LocalContext.current
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme

    val isActive = getTripStatus(
        trip.startDate,
        trip.endDate
    ) is TripStatus.Ongoing

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = trip.name,
                style = typography.titleLarge,
                color = colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            if (isActive) {
                Text(
                    text = stringResource(R.string.trips_trip_card_active_label),
                    style = typography.labelSmall,
                    color = colorScheme.onSecondary,
                    modifier = Modifier
                        .background(
                            color = colorScheme.secondary,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                )
            }
        }

        Text(
            text = formatTripDatesWithYear(
                context,
                trip.startDate,
                trip.endDate
            ),
            style = typography.bodyMedium,
            color = colorScheme.onSurfaceVariant
        )
    }

    Spacer(
        modifier = Modifier.height(28.dp)
    )
}