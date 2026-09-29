package com.example.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.DocumentPreview
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.ui.screens.transport_details.transport_timeline.TransportTimeline
import com.example.trippocket.utils.openFile

@Composable
fun TransportDetailsScreen(
    transport: Transport,
    onBackClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    onEditClick: (id: Long) -> Unit,
) {
    val context = LocalContext.current

    val title = when (transport.transportType) {
        TransportType.BUS ->
            stringResource(R.string.transport_details_bus_info_title)

        TransportType.TRAIN ->
            stringResource(R.string.transport_details_train_info_title)
    }

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
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TransportInfo(transport = transport)

            Spacer(modifier = Modifier.height(40.dp))

            TransportTimeline(
                transport = transport
            )

            Spacer(modifier = Modifier.height(40.dp))

            transport.document?.let {
                Text(
                    stringResource(R.string.transport_details_documents_section_title),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                DocumentPreview(
                    path = it.path,
                    fileName = it.name,
                    onClick = {
                        openFile(
                            context = context,
                            path = it.path
                        )
                    }
                )
            }
        }
    }
}