package com.example.trippocket.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TripFilterToggle(
    showUpcoming: Boolean,
    onUpcomingClick: () -> Unit,
    onPastClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onUpcomingClick)
                .background(
                    if (showUpcoming) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        Color.Transparent
                    }
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Upcoming")
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onPastClick)
                .background(
                    if (!showUpcoming) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        Color.Transparent
                    }
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Past")
        }
    }
}