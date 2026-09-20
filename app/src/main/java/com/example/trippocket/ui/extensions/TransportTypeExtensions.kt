package com.example.trippocket.ui.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trippocket.data.model.TransportType

fun TransportType.toIcon(): ImageVector {
    return when (this) {
        TransportType.BUS -> Icons.Default.DirectionsBus
        TransportType.TRAIN -> Icons.Default.Train
    }
}

fun TransportType.toDisplayName(): String {
    return when (this) {
        TransportType.BUS -> "Bus"
        TransportType.TRAIN -> "Train"
    }
}