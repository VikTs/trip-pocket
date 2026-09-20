package com.example.trippocket.ui.screens.trip_details

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
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.extensions.toIcon

@Composable
fun TimelineNode(
    transportType: TransportType,
    isFirst: Boolean,
    isLast: Boolean
) {
    Box(
        modifier = Modifier
            .width(32.dp)
            .height(120.dp)
    ) {
        if (!isFirst) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(60.dp)
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        }

        if (!isLast) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = transportType.toIcon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}