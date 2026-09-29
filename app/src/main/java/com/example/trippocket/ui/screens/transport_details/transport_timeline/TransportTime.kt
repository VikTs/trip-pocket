package com.example.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.utils.formatTripTime
import java.time.LocalDateTime

@Composable
fun TransportTime(
    time: LocalDateTime,
    dayOffset: Long = 0,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = formatTripTime(time),
            style = MaterialTheme.typography.titleMedium
        )

        if (dayOffset != 0L) {
            Spacer(
                modifier = Modifier.width(4.dp)
            )

            Text(
                text = if (dayOffset > 0) {
                    "+$dayOffset"
                } else {
                    dayOffset.toString()
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}