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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    fun getTripStatus(
        startDate: LocalDate,
        endDate: LocalDate
    ): String {
        val today = LocalDate.now()

        return when {
            today.isBefore(startDate) -> {
                val daysUntil = ChronoUnit.DAYS.between(today, startDate)
                "In $daysUntil days"
            }

            !today.isAfter(endDate) -> {
                "Today"
            }

            else -> {
                "Completed"
            }
        }
    }

    fun getTripDuration(
        startDate: LocalDate,
        endDate: LocalDate
    ): Long {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1
    }

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
                text = "${getTripDuration(trip.startDate, trip.endDate)} days  |  ${
                    formatTripDates(
                        startDate = trip.startDate,
                        endDate = trip.endDate
                    )
                }",
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
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = null,
                    tint = colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = getTripStatus(
                        startDate = trip.startDate,
                        endDate = trip.endDate
                    ),
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}