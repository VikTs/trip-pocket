package com.viktoriia.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Transport
import java.time.Duration
import java.time.temporal.ChronoUnit

@Composable
fun TransportTimelineSection(
    transport: Transport,
    modifier: Modifier = Modifier
) {
    val duration = Duration.between(
        transport.from.time,
        transport.to.time
    )

    val totalMinutes = duration.toMinutes()

    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    val arrivalDayOffset = ChronoUnit.DAYS.between(
        transport.from.time.toLocalDate(),
        transport.to.time.toLocalDate()
    )

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        TimelineLine(Modifier.height(115.dp))

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .height(210.dp)
        ) {
            TransportLocation(
                city = transport.from.city,
                address = transport.from.address,
                modifier = Modifier.height(80.dp)
            )

            TransportDuration(
                hours = hours,
                minutes = minutes,
                modifier = Modifier.height(50.dp)
            )

            TransportLocation(
                city = transport.to.city,
                address = transport.to.address,
                modifier = Modifier.height(80.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Column(
            modifier = Modifier
                .height(200.dp)
                .padding(top = 2.dp),
            horizontalAlignment = Alignment.End
        ) {
            TransportTime(
                time = transport.from.time,
                modifier = Modifier.height(50.dp)
            ) {
                PlatformText(transport.from.platform)
            }

            Spacer(
                modifier = Modifier.height(80.dp)
            )

            TransportTime(
                time = transport.to.time,
                dayOffset = arrivalDayOffset,
                modifier = Modifier.height(50.dp)
            ) {
                PlatformText(transport.to.platform)
            }
        }
    }
}

@Composable
fun PlatformText(
    platform: String?,
    modifier: Modifier = Modifier
) {
    platform?.let {
        Text(
            text = "${stringResource(R.string.transport_details_platform_label)}: $platform",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier
        )
    }
}