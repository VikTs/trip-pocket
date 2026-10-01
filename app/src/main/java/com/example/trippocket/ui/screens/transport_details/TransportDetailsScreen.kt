package com.example.trippocket.ui.screens.transport_details

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
import com.example.trippocket.R
import com.example.trippocket.data.model.TransportNotification
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportDocument
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.ui.components.rememberFilePicker
import com.example.trippocket.ui.screens.transport_details.transport_timeline.TransportTimelineSection
import com.example.trippocket.viewmodel.TransportsViewModel

@Composable
fun TransportDetailsScreen(
    transport: Transport,
    documents: List<TransportDocument>,
    notification: TransportNotification?,
    viewModel: TransportsViewModel,
    onBackClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    onEditClick: (id: Long) -> Unit,
) {
    val title = when (transport.transportType) {
        TransportType.BUS ->
            stringResource(R.string.transport_details_bus_info_title)

        TransportType.TRAIN ->
            stringResource(R.string.transport_details_train_info_title)
    }

    val context = LocalContext.current

    val pickFile = rememberFilePicker(
        context = context,
        onFileSelected = { document ->
            viewModel.addDocument(
                transportId = transport.id,
                name = document.name,
                path = document.path
            )
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = title,
                actions = {
                    TransportActionsMenu(
                        transportId = transport.id,
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TransportInfoSection(
                transport = transport
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            TransportTimelineSection(
                transport = transport,
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            TransportDocumentsSection(
                documents = documents,
                onAddDocument = pickFile
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            TransportNotificationsSection(
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

