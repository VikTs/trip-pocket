package com.example.trippocket.ui.screens.transport_details.transport_timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.ui.components.AddressLink

@Composable
fun TransportLocation(
    city: String,
    address: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = city,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        AddressLink(
            address = address,
            additionalInfo = city
        )
    }
}