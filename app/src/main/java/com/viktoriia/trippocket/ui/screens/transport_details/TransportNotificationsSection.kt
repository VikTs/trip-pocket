package com.viktoriia.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.TransportNotification
import com.viktoriia.trippocket.data.model.TransportType
import com.viktoriia.trippocket.ui.components.inputs.LabeledSwitch
import com.viktoriia.trippocket.ui.components.sections.NotificationsSection

@Composable
fun TransportNotificationsSection(
    transportType: TransportType,
    notification: TransportNotification?,
    onNotificationToggle: (Boolean) -> Unit,
) {
    NotificationsSection(
        title = stringResource(
            R.string.transport_details_notifications_section_title
        ),
    ) {
        if (notification == null) {
            Text(
                text = stringResource(
                    R.string.transport_details_notifications_unavailable
                )
            )
        } else {
            val minutesBefore = notification.minutesBefore
            val hours = minutesBefore / 60
            val minutes = minutesBefore % 60

            val notificationTime = when {
                hours > 0 && minutes > 0 -> stringResource(
                    R.string.duration_hours_minutes_short,
                    hours.toInt(),
                    minutes.toInt()
                )

                hours > 0 -> stringResource(
                    R.string.duration_hours_short,
                    hours.toInt()
                )

                else -> stringResource(
                    R.string.duration_minutes_short,
                    minutes.toInt()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LabeledSwitch(
                    label = stringResource(
                         when (transportType) {
                            TransportType.PLANE ->
                                R.string.transport_details_plane_departure_notification_label

                            else ->
                                R.string.transport_details_departure_notification_label
                        },
                        notificationTime
                    ),
                    checked = notification.enabled,
                    onCheckedChange = onNotificationToggle
                )
            }
        }
    }
}