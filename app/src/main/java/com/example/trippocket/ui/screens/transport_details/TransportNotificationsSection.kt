package com.example.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.ui.components.inputs.LabeledSwitch
import com.example.trippocket.ui.components.sections.NotificationsSection

@Composable
fun TransportNotificationsSection(
    isNotificationEnabled: Boolean,
    onNotificationToggle: (Boolean) -> Unit,
) {
    NotificationsSection(
        title = stringResource(
            R.string.transport_details_notifications_section_title
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabeledSwitch(
                label = stringResource(R.string.transport_details_departure_notification_label),
                checked = isNotificationEnabled,
                onCheckedChange = onNotificationToggle
            )
        }
    }
}
