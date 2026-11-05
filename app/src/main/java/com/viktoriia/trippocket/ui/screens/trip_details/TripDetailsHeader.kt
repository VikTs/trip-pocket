package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.ui.utils.formatTripDatesWithYear

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
        Text(
            text = trip.name,
            style = typography.titleLarge,
            color = colorScheme.onSurface
        )

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
        modifier = Modifier.height(24.dp)
    )
}