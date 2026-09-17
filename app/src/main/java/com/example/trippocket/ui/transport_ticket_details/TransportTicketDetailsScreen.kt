package com.example.trippocket.ui.transport_ticket_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.ui.components.TopBar

@Composable
fun TransportTicketDetailsScreen(
    transportTicket: TransportTicket,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                title = "Transport details",
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
        ) {
            Text(
                text = "${transportTicket.from.city} - ${transportTicket.to.city}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}