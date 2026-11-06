package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimelineNode(
    icon: ImageVector,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean,
    isNext: Boolean,
) {
    val colorScheme = MaterialTheme.colorScheme
    val boxHeight = if(isNext) 160.dp else 130.dp

    val nodeColor =
        if (isNext)
            colorScheme.onSecondary
        else if (isActive) colorScheme.primary
        else colorScheme.onSurfaceVariant

    val prevNodeColor =
        if (isPrevActive) colorScheme.primary
        else colorScheme.onSurfaceVariant

    val nextLineColor =
        if (isActive) colorScheme.primary
        else colorScheme.onSurfaceVariant

    val backgroundColor =
        if (isNext)
            colorScheme.secondary
        else if (isActive)
            colorScheme.primaryContainer
        else colorScheme.surfaceContainer

    Box(
        modifier = Modifier
            .width(32.dp)
            .height(boxHeight)
    ) {
        if (!isFirst) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(boxHeight/2)
                    .align(Alignment.TopCenter)
                    .background(prevNodeColor)
            )
        }

        if (!isLast) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(boxHeight/2)
                    .align(Alignment.BottomCenter)
                    .background(nextLineColor)
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = nodeColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = nodeColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}