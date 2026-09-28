package com.example.trippocket.ui.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.R

fun TransportType.toIcon(): ImageVector {
    return when (this) {
        TransportType.BUS -> Icons.Default.DirectionsBus
        TransportType.TRAIN -> Icons.Default.Train
    }
}

@Composable
fun TransportType.toDisplayName(): String {
    return when (this) {
        TransportType.BUS ->
            stringResource(R.string.transport_type_bus)

        TransportType.TRAIN ->
            stringResource(R.string.transport_type_train)
    }
}