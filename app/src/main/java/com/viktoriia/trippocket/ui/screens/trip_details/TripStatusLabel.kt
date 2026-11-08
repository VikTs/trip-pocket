package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.domain.model.TripStatus
import com.viktoriia.trippocket.domain.model.getTripStatus
import com.viktoriia.trippocket.ui.theme.PrimaryLight
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun TripStatusLabel(
    trip: Trip,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val status = getTripStatus(trip.startDate, trip.endDate)

    val (text, backgroundColor, textColor) = when (status) {
        is TripStatus.Ongoing -> Triple(
            stringResource(R.string.trips_trip_card_active_label),
            colorScheme.secondary,
            colorScheme.onSecondary
        )

        is TripStatus.Upcoming -> {
            val daysLeft = ChronoUnit.DAYS.between(
                LocalDate.now(),
                trip.startDate
            ).toInt()

            Triple(
                pluralStringResource(
                    R.plurals.trips_trip_card_in_days_label,
                    daysLeft,
                    daysLeft
                ),
                PrimaryLight,
                colorScheme.secondary
            )
        }

        is TripStatus.Completed -> Triple(
            stringResource(R.string.trips_trip_card_completed_label),
            colorScheme.surfaceVariant,
            colorScheme.onSurfaceVariant
        )
    }

    val isCompleted = status is TripStatus.Completed

    val inlineContent = if (isCompleted) {
        mapOf(
            "check" to InlineTextContent(
                placeholder = Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
    } else {
        emptyMap()
    }

    val annotatedText = buildAnnotatedString {
        if (isCompleted) {
            appendInlineContent("check")
            append(" ")
        }
        append(text)
    }

    Text(
        text = annotatedText,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.labelSmall,
        color = textColor,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}