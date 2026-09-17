package com.example.trippocket.ui.add_transport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportStop
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.viewmodel.TripDetailsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    tripId: Long,
    onBackClick: () -> Unit,
    viewModel: TripDetailsViewModel,
) {
    val context = LocalContext.current
    var transportType by remember {
        mutableStateOf(TransportType.BUS)
    }

    var fromCity by remember {
        mutableStateOf("")
    }

    var fromDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var fromTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    var toCity by remember {
        mutableStateOf("")
    }

    var toDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var toTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    var documentPath by remember {
        mutableStateOf<String?>(null)
    }

    var documentName by remember {
        mutableStateOf<String?>(null)
    }

    fun onAddTransport() {
        val ticket = TransportTicket(
            tripId = tripId,
            type = transportType,
            documentPath = documentPath,
            from = TransportStop(
                city = fromCity.trim(),
                time = LocalDateTime.of(
                    fromDate,
                    fromTime
                ),
            ),
            to = TransportStop(
                city = toCity.trim(),
                time = LocalDateTime.of(
                    toDate,
                    toTime
                ),
            )
        )

        viewModel.addTicket(ticket)
        onBackClick()
    }

    Scaffold(
        topBar = {
            TopBar(title = "Add transport", onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TransportInputSection(
                transportType,
                onTransportTypeChange = {
                    transportType = it
                },
            )

            TransportStopInputSection(
                title = "From",
                city = fromCity,
                date = fromDate,
                time = fromTime,
                minDate = fromDate,
                onCityChange = { fromCity = it },
                onDateChange = { fromDate = it },
                onTimeChange = { fromTime = it }
            )

            TransportStopInputSection(
                title = "To",
                city = toCity,
                date = toDate,
                time = toTime,
                minDate = fromDate,
                onCityChange = { toCity = it },
                onDateChange = { toDate = it },
                onTimeChange = { toTime = it }
            )

            TicketInputSection(
                context = context,
                documentName = documentName,
                onDocumentPathChange = { documentPath = it },
                onDocumentNameChange = { documentName = it },
            )

            Button(
                onClick = { onAddTransport() },
                modifier = Modifier.fillMaxWidth(),
                enabled = fromCity.trim().isNotBlank() &&
                        toCity.trim().isNotBlank() &&
                        fromDate != null &&
                        toDate != null &&
                        fromTime != null &&
                        toTime != null
            ) {
                Text("Add transport")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}