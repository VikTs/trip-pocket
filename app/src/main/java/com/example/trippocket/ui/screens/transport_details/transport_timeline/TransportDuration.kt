package com.example.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R

@Composable
fun TransportDuration(
    hours: Long,
    minutes: Long,
    modifier: Modifier = Modifier
) {
    val durationText = when {
        hours > 0 && minutes > 0 -> {
            "${
                pluralStringResource(
                    R.plurals.transport_duration_hours,
                    hours.toInt(),
                    hours
                )
            } ${
                pluralStringResource(
                    R.plurals.transport_duration_minutes,
                    minutes.toInt(),
                    minutes
                )
            }"
        }

        hours > 0 -> {
            pluralStringResource(
                R.plurals.transport_duration_hours,
                hours.toInt(),
                hours
            )
        }

        else -> {
            pluralStringResource(
                R.plurals.transport_duration_minutes,
                minutes.toInt(),
                minutes
            )
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = durationText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}