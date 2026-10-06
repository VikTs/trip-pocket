package com.viktoriia.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.data.model.AccommodationDocument
import com.viktoriia.trippocket.data.model.AccommodationNotification
import com.viktoriia.trippocket.data.model.AccommodationNotificationType
import com.viktoriia.trippocket.ui.components.ItemEditMenu
import com.viktoriia.trippocket.ui.components.TopBar
import com.viktoriia.trippocket.ui.components.inputs.rememberFilePicker
import com.viktoriia.trippocket.viewmodel.AccommodationViewModel

@Composable
fun AccommodationDetailsScreen(
    accommodation: Accommodation,
    documents: List<AccommodationDocument>,
    notifications: List<AccommodationNotification>?,
    viewModel: AccommodationViewModel,
    onBackClick: () -> Unit,
    onEditClick: (accommodationId: Long) -> Unit,
    onDeleteClick: (accommodationId: Long) -> Unit
) {
    val checkInNotification =
        notifications?.firstOrNull {
            it.type == AccommodationNotificationType.CHECK_IN
        }

    val checkOutNotification =
        notifications?.firstOrNull {
            it.type == AccommodationNotificationType.CHECK_OUT
        }

    val context = LocalContext.current
    val pickFile = rememberFilePicker(
        context = context,
        onFileSelected = { document ->
            viewModel.addDocument(
                accommodationId = accommodation.id,
                name = document.name,
                path = document.path
            )
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.accommodation_details_title),
                onBackClick = onBackClick,
                actions = {
                    ItemEditMenu(
                        itemId = accommodation.id,
                        itemDeletionTitle = stringResource(R.string.accommodation_details_delete_confirmation_title),
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
                .verticalScroll(rememberScrollState())
        ) {
            AccommodationDetailsHeader(accommodation)

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            AccommodationInfoSection(accommodation)

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            AccommodationDocumentsSection(
                documents = documents,
                onAddDocument = pickFile
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            AccommodationNotificationsSection(
                checkInNotification = checkInNotification,
                checkOutNotification = checkOutNotification,
                onNotificationToggle = { type, enabled ->
                    notifications
                        ?.firstOrNull { it.type == type }
                        ?.let {
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