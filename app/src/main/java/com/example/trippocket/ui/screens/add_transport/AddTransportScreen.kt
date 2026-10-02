package com.example.trippocket.ui.screens.add_transport

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportStop
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.SelectedDocument
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.viewmodel.TransportsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    tripId: Long,
    onBackClick: () -> Unit,
    viewModel: TransportsViewModel,
    transportId: Long? = null,
) {
    val editTransport =
        transportId?.let { id ->
            viewModel.transports.value.firstOrNull { it.id == id }
        }

    val isEditMode = editTransport != null

    val documents = transportId?.let {
        viewModel.getDocuments(it)
            .collectAsState(initial = emptyList())
            .value
    } ?: emptyList()

    var state by remember(editTransport, documents) {
        mutableStateOf(
            editTransport?.let { transport ->
                AddTransportState(
                    transportType = transport.transportType,
                    transportNumber = transport.transportNumber.orEmpty(),
                    place = transport.place.orEmpty(),
                    coach = transport.coach.orEmpty(),
                    isPrivateTransport = transport.isPrivateTransport,
                    carrier = transport.carrier.orEmpty(),
                    driverPhone = transport.driverPhone.orEmpty(),
                    fromCity = transport.from.city,
                    fromDate = transport.from.time.toLocalDate(),
                    fromTime = transport.from.time.toLocalTime(),
                    fromAddress = transport.from.address,
                    fromPlatform = transport.from.platform.orEmpty(),
                    toCity = transport.to.city,
                    toDate = transport.to.time.toLocalDate(),
                    toTime = transport.to.time.toLocalTime(),
                    toAddress = transport.to.address,
                    toPlatform = transport.to.platform.orEmpty(),
                    documents = documents.map {
                        SelectedDocument(
                            name = it.name,
                            path = it.path
                        )
                    }
                )
            } ?: AddTransportState()
        )
    }

    fun onSaveTransport() {
        val transport = Transport(
            id = editTransport?.id ?: 0,
            tripId = tripId,
            transportType = state.transportType,
            transportNumber = state.transportNumber?.ifBlank { null },
            coach = state.coach?.ifBlank { null },
            place = state.place?.ifBlank { null },
            carrier = state.carrier?.ifBlank { null },
            driverPhone = state.driverPhone?.ifBlank { null },
            isPrivateTransport = state.isPrivateTransport,
            from = TransportStop(
                city = state.fromCity.trim(),
                time = LocalDateTime.of(
                    state.fromDate,
                    state.fromTime
                ),
                address = state.fromAddress.trim(),
                platform = state.fromPlatform?.trim()?.ifBlank { null }
            ),
            to = TransportStop(
                city = state.toCity.trim(),
                time = LocalDateTime.of(
                    state.toDate,
                    state.toTime
                ),
                address = state.toAddress.trim(),
                platform = state.toPlatform?.trim()?.ifBlank { null }
            )
        )

        if (isEditMode) {
            viewModel.updateTransport(
                transport = transport,
                documents = state.documents
            )
        } else {
            viewModel.addTransport(
                transport = transport,
                documents = state.documents
            )
        }

        onBackClick()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEditMode) {
                    stringResource(R.string.edit_transport_title)
                } else {
                    stringResource(R.string.add_transport_title)
                },
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
                isPrivateBus = state.isPrivateTransport,
                carrier = state.carrier,
                driverPhone = state.driverPhone,
                onPrivateBusChange = {
                    state = state.copy(
                        isPrivateTransport = it,
                        carrier = null,
                        driverPhone = null
                    )
                },
                onCarrierChange = {
                    state = state.copy(
                        carrier = it
                    )
                },
                onDriverPhoneChange = {
                    state = state.copy(
                        driverPhone = it?.filter { char ->
                            char.isDigit() || char == '+'
                        }
                    )
                },
                onTransportTypeChange = {
                    if (it != state.transportType) {
                        state = state.copy(
                            transportType = it,
                            transportNumber = null,
                            place = null,
                            coach = null
                        )
                    }
                },
                onTransportNumberChange = {
                    state = state.copy(
                        transportNumber = it
                    )
                },
                onCoachChange = {
                    state = state.copy(
                        coach = it
                    )
                },
                onPlaceChange = {
                    state = state.copy(
                        place = it
                    )
                }
            )

            TransportStopInputSection(
                title = stringResource(
                    R.string.add_transport_from_section_title
                ),
                city = state.fromCity,
                date = state.fromDate,
                time = state.fromTime,
                address = state.fromAddress,
                platform = state.fromPlatform,
                minDate = null,
                onCityChange = {
                    state = state.copy(
                        fromCity = it
                    )
                },
                onDateChange = {
                    state = state.copy(
                        fromDate = it
                    )
                },
                onTimeChange = {
                    state = state.copy(
                        fromTime = it
                    )
                },
                onAddressChange = {
                    state = state.copy(
                        fromAddress = it
                    )
                },
                onPlatformChange = {state = state.copy(
                    fromPlatform = it
                )}
            )

            TransportStopInputSection(
                title = stringResource(
                    R.string.add_transport_to_section_title
                ),
                city = state.toCity,
                date = state.toDate,
                time = state.toTime,
                address = state.toAddress,
                platform = state.toPlatform,
                minDate = state.fromDate,
                onCityChange = {
                    state = state.copy(
                        toCity = it
                    )
                },
                onDateChange = {
                    state = state.copy(
                        toDate = it
                    )
                },
                onTimeChange = {
                    state = state.copy(
                        toTime = it
                    )
                },
                onAddressChange = {
                    state = state.copy(
                        toAddress = it
                    )
                },
                onPlatformChange = {state = state.copy(
                    toPlatform = it
                )}
            )

            TransportDocumentsSection(
                documents = state.documents,
                onAddDocument = { document ->
                    state = state.copy(
                        documents = state.documents + document
                    )
                },
                onRemoveDocument = { document ->
                    state = state.copy(
                        documents = state.documents.filterNot {
                            it.path == document.path
                        }
                    )
                }
            )

            Button(
                onClick = ::onSaveTransport,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isValid
            ) {
                Text(
                    if (isEditMode) {
                        stringResource(R.string.common_save_changes)
                    } else {
                        stringResource(R.string.add_transport_btn_label)
                    }
                )
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
    val isPrivateTransport: Boolean = false,
    val carrier: String? = null,
    val driverPhone: String? = null,
    val coach: String? = null,
    val fromCity: String = "",
    val fromDate: LocalDate? = null,
    val fromTime: LocalTime? = null,
    val fromAddress: String = "",
    val fromPlatform: String? = null,
    val toCity: String = "",
    val toDate: LocalDate? = null,
    val toTime: LocalTime? = null,
    val toAddress: String = "",
    val toPlatform: String? = null,
    val documents: List<SelectedDocument> = emptyList()
) {
    val isValid: Boolean
        get() =
            fromCity.trim().isNotBlank() &&
                    toCity.trim().isNotBlank() &&
                    fromAddress.trim().isNotBlank() &&
                    toAddress.trim().isNotBlank() &&
                    fromDate != null &&
                    fromTime != null &&
                    toDate != null &&
                    toTime != null &&
                    (transportType != TransportType.TRAIN || !transportNumber.isNullOrBlank())
}