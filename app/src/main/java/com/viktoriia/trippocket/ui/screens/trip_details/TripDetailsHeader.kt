package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.utils.formatTripDates

@Composable
fun TripDetailsHeader(
    trip: Trip
) {
    val context = LocalContext.current
    Text(
        text = trip.name,
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )

    Text(
        text = formatTripDates(
            context,
            startDate = trip.startDate,
            endDate = trip.endDate
        ),
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(
        modifier = Modifier.height(24.dp)
    )
}