package com.example.trippocket.ui.screens.trips

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        ToggleItem(
            text = "Upcoming",
            selected = showUpcoming,
            onClick = onUpcomingClick,
            modifier = Modifier.weight(1f)
        )

        ToggleItem(
            text = "Past",
            selected = !showUpcoming,
            onClick = onPastClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ToggleItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val background = if (selected) colors.surface else colors.surfaceVariant
    val textColor = if (selected) colors.primary else colors.onSurfaceVariant
    val textStyle = if (selected) {
        MaterialTheme.typography.titleMedium
    } else {
        MaterialTheme.typography.bodyLarge
    }

    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(background)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            lineHeight = 18.sp
        )
    }
}