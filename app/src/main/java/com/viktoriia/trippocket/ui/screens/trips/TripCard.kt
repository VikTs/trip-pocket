package com.viktoriia.trippocket.ui.screens.trips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.utils.formatTripDates
import java.time.temporal.ChronoUnit

@Composable
fun TripCard(
    trip: Trip,
    onClick: () -> Unit
) {
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    val tripStatus = getTripStatus(
        startDate = trip.startDate,
        endDate = trip.endDate
    )

    val statusText = when (tripStatus) {
        is TripStatus.Upcoming -> {
            pluralStringResource(
                R.plurals.trips_trip_card_in_days_label,
                tripStatus.daysUntil.toInt(),
                tripStatus.daysUntil.toInt()
            )
        }

        TripStatus.Ongoing -> {
            stringResource(R.string.trips_trip_card_today_label)
        }

        TripStatus.Completed -> {
            stringResource(R.string.trips_trip_card_completed_label)
        }
    }

    val isCompleted = tripStatus is TripStatus.Completed

    val tripDuration = (
            ChronoUnit.DAYS.between(
                trip.startDate,
                trip.endDate
            ) + 1
            ).toInt()

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (trip.imagePath == null) {
                    painterResource(R.drawable.trip)
                } else {
                    rememberAsyncImagePainter(trip.imagePath)
                },
                contentDescription = trip.name,
                modifier = Modifier.size(88.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = trip.name,
                    style = typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = pluralStringResource(
                        R.plurals.trips_trip_card_duration_and_dates_label,
                        tripDuration,
                        tripDuration,
                        formatTripDates(
                            context,
                            startDate = trip.startDate,
                            endDate = trip.endDate
                        )
                    ),
                    style = typography.bodyMedium,
                    color = colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isCompleted) {
                            Icons.Outlined.TaskAlt
                        } else {
                            Icons.Outlined.AccessTime
                        },
                        contentDescription = null,
                        tint = colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = statusText,
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}