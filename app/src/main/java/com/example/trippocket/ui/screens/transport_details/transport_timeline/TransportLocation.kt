package com.example.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransportLocation(
    city: String,
    address: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = city,
            style = MaterialTheme.typography.titleLarge
        )

        address
            ?.takeIf { it.isNotBlank() }
            ?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
    }
}