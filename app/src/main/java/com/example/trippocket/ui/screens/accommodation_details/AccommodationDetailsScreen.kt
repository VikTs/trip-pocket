package com.example.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.AccommodationNotification
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.viewmodel.AccommodationViewModel

@Composable
fun AccommodationDetailsScreen(
    accommodation: Accommodation,
    notification: AccommodationNotification?,
    viewModel: AccommodationViewModel,
    onBackClick: () -> Unit,
    onEditClick: (accommodationId: Long) -> Unit,
    onDeleteClick: (accommodationId: Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.accommodation_details_title),
                onBackClick = onBackClick,
                actions = {
                    AccommodationActionsMenu(
                        accommodationId = accommodation.id,
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AccommodationDetailsHeader(accommodation)

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            AccommodationInfoSection(accommodation)

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            AccommodationNotificationsSection(
                isNotificationEnabled = notification?.enabled == true,
                onNotificationToggle = { enabled ->
                    notification?.let {
                        viewModel.updateNotification(
                            notification = it.copy(
                                enabled = enabled
                            )
                        )
                    }
                }
            )
        }
    }
}