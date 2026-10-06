package com.viktoriia.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.utils.formatTripTime
import java.time.LocalDateTime

@Composable
fun TransportTime(
    time: LocalDateTime,
    dayOffset: Long = 0,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
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

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        content?.invoke()
    }
}