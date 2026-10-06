package com.viktoriia.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TimelineLine(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(top = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimelinePoint()

        Box(
            modifier = modifier
                .width(2.dp)
                .background(
                    MaterialTheme.colorScheme.outlineVariant
                )
        )

        TimelinePoint()
    }
}

@Composable
private fun TimelinePoint() {
    Box(
        modifier = Modifier
            .size(13.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    )
}