package com.example.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R

@Composable
fun TransportNotificationsSection(
    isNotificationEnabled: Boolean,
    onNotificationToggle: (Boolean) -> Unit
) {
    Text(
        text = stringResource(
            R.string.transport_details_notifications_section_title
        ),
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                R.string.transport_details_departure_notification_label
            ),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = isNotificationEnabled,
            onCheckedChange = onNotificationToggle
        )
    }
}
