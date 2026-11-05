package com.viktoriia.trippocket.ui.screens.trips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.ui.extensions.toDisplayName
import com.viktoriia.trippocket.ui.utils.formatTripDates
import com.viktoriia.trippocket.ui.utils.formatTripTime
import java.time.temporal.ChronoUnit

@Composable
fun TripCard(
    trip: Trip,
    onClick: () -> Unit,
    isActive: Boolean = false,
    nextEvent: TripEvent? = null
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

        is TripStatus.Ongoing -> {
            pluralStringResource(
                R.plurals.trips_trip_card_days_remaining_label,
                tripStatus.daysRemaining.toInt(),
                tripStatus.daysRemaining.toInt()
            )
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

    val nextEventLabel = when (val event = nextEvent) {
        is TripEvent.TransportItem -> stringResource(
            R.string.trips_trip_card_next_transport,
            event.transport.transportType.toDisplayName(),
            event.transport.to.city,
        )

        is TripEvent.AccommodationCheckInItem -> stringResource(
            R.string.trips_trip_card_next_accommodation_check_in,
            event.accommodation.name,
        )

        is TripEvent.AccommodationCheckOutItem -> stringResource(
            R.string.trips_trip_card_next_accommodation_check_out,
            event.accommodation.name,
            formatTripTime(event.time)
        )

        null -> null
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        border = if (isActive) BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.secondary
        ) else null,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = trip.name,
                        style = typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )

                    if (isActive) {
                        Text(
                            text = stringResource(R.string.trips_trip_card_active_label),
                            style = typography.labelSmall,
                            color = colorScheme.onSecondary,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .background(
                                    color = colorScheme.secondary,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

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

        nextEventLabel?.let {
            Card(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.secondary
                )
            ) {
                Text(
                    text = it,
                    style = typography.bodyMedium,
                    color = colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}