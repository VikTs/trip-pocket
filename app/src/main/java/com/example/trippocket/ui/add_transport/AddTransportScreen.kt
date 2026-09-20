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
import com.example.trippocket.viewmodel.TransportTicketsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    tripId: Long,
    onBackClick: () -> Unit,
    viewModel: TransportTicketsViewModel,
    transportId: Long? = null
) {
    val editTransport =
        transportId?.let { id -> viewModel.tickets.value.firstOrNull { it.id == id } }

    val isEditMode = editTransport != null
    val context = LocalContext.current
    var state by remember(editTransport) {
        mutableStateOf(editTransport?.let { ticket ->
            AddTransportState(
                transportType = ticket.transportType,
                transportNumber = ticket.transportNumber.orEmpty(),
                place = ticket.place.orEmpty(),
                coach = ticket.coach.orEmpty(),
                fromCity = ticket.from.city,
                fromDate = ticket.from.time.toLocalDate(),
                fromTime = ticket.from.time.toLocalTime(),
                fromAddress = ticket.from.address,
                toCity = ticket.to.city,
                toDate = ticket.to.time.toLocalDate(),
                toTime = ticket.to.time.toLocalTime(),
                toAddress = ticket.to.address,
                documentPath = ticket.documentPath
            )
        } ?: AddTransportState())
    }

    fun onSaveTransport() {
        val ticket = TransportTicket(
            id = editTransport?.id ?: 0,
            tripId = tripId,
            transportType = state.transportType,
            transportNumber = state.transportNumber?.ifBlank { null },
            coach = state.coach?.ifBlank { null },
            place = state.place?.ifBlank { null },
            documentPath = state.documentPath,
            from = TransportStop(
                city = state.fromCity.trim(),
                time = LocalDateTime.of(
                    state.fromDate,
                    state.fromTime
                ),
                address = state.fromAddress?.ifBlank { null }
            ),
            to = TransportStop(
                city = state.toCity.trim(),
                time = LocalDateTime.of(
                    state.toDate,
                    state.toTime
                ),
                address = state.toAddress?.ifBlank { null }
            )
        )

        if (isEditMode) {
            viewModel.updateTicket(ticket)
        } else {
            viewModel.addTicket(ticket)
        }

        onBackClick()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEditMode) "Edit transport" else "Add transport",
                onBackClick = onBackClick
            )
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
                transportType = state.transportType,
                transportNumber = state.transportNumber,
                coach = state.coach,
                place = state.place,
                onTransportTypeChange = {
                    if (it != state.transportType)
                        state = state.copy(
                            transportType = it,
                            transportNumber = null,
                            place = null,
                            coach = null
                        )
                },
                onTransportNumberChange = {
                    state = state.copy(transportNumber = it)
                },
                onCoachChange = {
                    state = state.copy(coach = it)
                },
                onPlaceChange = {
                    state = state.copy(place = it)
                }
            )

            TransportStopInputSection(
                title = "From",
                city = state.fromCity,
                date = state.fromDate,
                time = state.fromTime,
                address = state.fromAddress,
                minDate = null,
                onCityChange = { state = state.copy(fromCity = it) },
                onDateChange = { state = state.copy(fromDate = it) },
                onTimeChange = { state = state.copy(fromTime = it) },
                onAddressChange = { state = state.copy(fromAddress = it) },
            )

            TransportStopInputSection(
                title = "To",
                city = state.toCity,
                date = state.toDate,
                time = state.toTime,
                address = state.toAddress,
                minDate = state.fromDate,
                onCityChange = { state = state.copy(toCity = it) },
                onDateChange = { state = state.copy(toDate = it) },
                onTimeChange = { state = state.copy(toTime = it) },
                onAddressChange = { state = state.copy(toAddress = it) },
            )

            TicketInputSection(
                context = context,
                documentName = state.documentName,
                onDocumentPathChange = { state = state.copy(documentPath = it) },
                onDocumentNameChange = { state = state.copy(documentName = it) },
            )

            Button(
                onClick = ::onSaveTransport,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isValid
            ) {
                Text(if (isEditMode) "Edit transport" else "Add transport")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}

data class AddTransportState(
    val transportType: TransportType = TransportType.BUS,
    val transportNumber: String? = null,
    val place: String? = null,
    val coach: String? = null,
    val fromCity: String = "",
    val fromDate: LocalDate? = null,
    val fromTime: LocalTime? = null,
    val fromAddress: String? = null,
    val toCity: String = "",
    val toDate: LocalDate? = null,
    val toTime: LocalTime? = null,
    val toAddress: String? = null,
    val documentPath: String? = null,
    val documentName: String? = null
) {
    val isValid: Boolean
        get() =
            fromCity.trim().isNotBlank() &&
                    toCity.trim().isNotBlank() &&
                    fromDate != null &&
                    fromTime != null &&
                    toDate != null &&
                    toTime != null &&
                    (transportType != TransportType.TRAIN ||
                            !transportNumber.isNullOrBlank())
}