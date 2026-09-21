package com.example.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Transport
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.ui.extensions.toDisplayName
import com.example.trippocket.utils.openFile

@Composable
fun TransportDetailsScreen(
    transport: Transport,
    onBackClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    onEditClick: (id: Long) -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = "${transport.transportType.toDisplayName()} info",
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

            Spacer(modifier = Modifier.height(24.dp))

            transport.ticketPath?.let {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = {
                        openFile(
                            context = context,
                            path = it
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Open ticket")
                }

            }
        }
    }
}