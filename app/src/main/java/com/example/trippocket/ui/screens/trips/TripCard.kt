package com.example.trippocket.ui.screens.trips

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Trip
import com.example.trippocket.utils.formatTripDates
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun TripCard(
    trip: Trip,
    onClick: () -> Unit
) {
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme

    val isCompleted = LocalDate.now().isAfter(trip.endDate)

    val tripStatus = when {
        LocalDate.now().isBefore(trip.startDate) -> {
            val daysUntil = ChronoUnit.DAYS.between(
                LocalDate.now(),
                trip.startDate
            ).toInt()

            pluralStringResource(
                R.plurals.trips_trip_card_in_days_label,
                daysUntil,
                daysUntil
            )
        }

        !isCompleted -> {
            stringResource(R.string.trips_trip_card_today_label)
        }

        else -> {
            stringResource(R.string.trips_trip_card_completed_label)
        }
    }

    val tripDuration = ChronoUnit.DAYS.between(
        trip.startDate,
        trip.endDate
    ) + 1

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = trip.name,
                style = typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = stringResource(
                    R.string.trips_trip_card_duration_and_dates_label,
                    tripDuration,
                    formatTripDates(
                        startDate = trip.startDate,
                        endDate = trip.endDate
                    )
                ),
                style = typography.bodyMedium,
                color = colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

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
                    text = tripStatus,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}