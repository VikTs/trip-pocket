package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.utils.formatTripMonth
import java.time.LocalDate

@Composable
fun TripDayFilters(
    days: List<LocalDate>,
    selectedDay: String?,
    onDaySelected: (String?) -> Unit
) {
    val context = LocalContext.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            DayFilterItem(
                month = "",
                day = stringResource(R.string.trip_details_day_filter_all_label),
                selected = selectedDay == null,
                onClick = { onDaySelected(null) }
            )
        }

        items(days) { date ->
            val dayKey = date.toString()

            DayFilterItem(
                month = formatTripMonth(context, date),
                day = date.dayOfMonth.toString(),
                selected = selectedDay == dayKey,
                onClick = { onDaySelected(dayKey) }
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DayFilterItem(
    month: String,
    day: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val backgroundColor = if (selected) {
        colors.primary
    } else {
        colors.surface
    }

    val borderColor = if (selected) {
        colors.primary
    } else {
        colors.outlineVariant
    }

    val monthTextColor = if (selected) {
        colors.onPrimary
    } else {
        colors.onSurfaceVariant
    }

    val dayTextColor = if (selected) {
        colors.onPrimary
    } else {
        colors.onSurface
    }

    Column(
        modifier = Modifier
            .width(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (month.isEmpty()) {
            Box(
                modifier = Modifier.height(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = typography.labelLarge,
                    color = dayTextColor
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (month.isNotEmpty()) {
                    Text(
                        text = month,
                        style = typography.labelSmall,
                        color = monthTextColor
                    )
                }

                Text(
                    text = day.padStart(2, '0'),
                    style = typography.titleSmall,
                    color = dayTextColor
                )
            }
        }
    }
}