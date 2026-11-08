package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.ui.utils.formatDatesRangeWithYear

@Composable
fun TripDetailsHeader(
    trip: Trip,
) {
    val context = LocalContext.current
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme

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

            TripStatusLabel(
                trip = trip,
            )
        }

        Text(
            text = formatDatesRangeWithYear(
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