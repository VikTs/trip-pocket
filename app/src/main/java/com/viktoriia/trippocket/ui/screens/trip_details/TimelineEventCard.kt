package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.utils.formatTime
import java.time.LocalDateTime

@Composable
fun TimelineEventCard(
    time: LocalDateTime,
    icon: ImageVector,
    onClick: () -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean,
    isNext: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatTime(time),
            style = typography.titleSmall,
            color = if (isNext) colors.secondary else Color.Unspecified
        )

        Spacer(modifier = Modifier.width(8.dp))

        TimelineNode(
            icon = icon,
            isFirst = isFirst,
            isLast = isLast,
            isActive = isActive,
            isPrevActive = isPrevActive,
            isNext = isNext
        )

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            colors = if (isNext) {
                CardDefaults.cardColors(containerColor = colors.secondary)
            } else {
                CardDefaults.cardColors()
            },
            onClick = onClick
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (isNext) {
                    Text(
                        text = stringResource(R.string.trip_details_next_event_label),
                        style = typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                content()
            }
        }
    }
}