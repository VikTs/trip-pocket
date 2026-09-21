package com.example.trippocket.ui.screens.transport_ticket_details

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
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.ui.extensions.toDisplayName
import com.example.trippocket.utils.openFile

@Composable
fun TransportTicketDetailsScreen(
    transportTicket: TransportTicket,
    onBackClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    onEditClick: (id: Long) -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = "${transportTicket.transportType.toDisplayName()} info",
                actions = {
                    TransportTicketActionsMenu(
                        transportTicketId = transportTicket.id,
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
            TransportInfo(transportTicket = transportTicket)

            Spacer(modifier = Modifier.height(40.dp))

            TransportTimeline(
                ticket = transportTicket
            )

            Spacer(modifier = Modifier.height(24.dp))

            transportTicket.documentPath?.let {
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