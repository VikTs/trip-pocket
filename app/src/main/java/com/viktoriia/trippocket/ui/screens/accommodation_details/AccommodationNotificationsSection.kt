package com.viktoriia.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.AccommodationNotification
import com.viktoriia.trippocket.data.model.AccommodationNotificationType
import com.viktoriia.trippocket.ui.components.inputs.LabeledSwitch
import com.viktoriia.trippocket.ui.components.sections.NotificationsSection

@Composable
fun AccommodationNotificationsSection(
    checkInNotification: AccommodationNotification?,
    checkOutNotification: AccommodationNotification?,
    onNotificationToggle: (
        AccommodationNotificationType,
        Boolean
    ) -> Unit
) {
    NotificationsSection(
        title = stringResource(
            R.string.accommodation_details_notifications_section_title
        )
    ) {
        LabeledSwitch(
            label = stringResource(
                R.string.accommodation_details_check_in_notification_label
            ),
            checked = checkInNotification?.enabled == true,
            onCheckedChange = {
                onNotificationToggle(
                    AccommodationNotificationType.CHECK_IN,
                    it
                )
            }
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        LabeledSwitch(
            label = stringResource(
                R.string.accommodation_details_check_out_notification_label
            ),
            checked = checkOutNotification?.enabled == true,
            onCheckedChange = {
                onNotificationToggle(
                    AccommodationNotificationType.CHECK_OUT,
                    it
                )
            }
        )
    }
}
