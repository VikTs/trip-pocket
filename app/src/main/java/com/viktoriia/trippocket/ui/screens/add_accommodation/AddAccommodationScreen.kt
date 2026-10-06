package com.viktoriia.trippocket.ui.screens.add_accommodation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument
import com.viktoriia.trippocket.ui.components.TopBar
import com.viktoriia.trippocket.viewmodel.AccommodationViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AddAccommodationScreen(
    tripId: Long,
    viewModel: AccommodationViewModel,
    accommodation: Accommodation? = null,
    onBackClick: () -> Unit,
) {
    val isEditMode = accommodation != null

    val documents = accommodation?.let {
        viewModel.getDocuments(accommodation.id)
            .collectAsState(initial = emptyList())
            .value
    } ?: emptyList()

    var state by remember(accommodation, documents) {
        mutableStateOf(
            accommodation?.let {
                AddAccommodationState(
                    name = it.name,
                    address = it.address,
                    contactPhone = it.contactPhone.orEmpty(),
                    checkInDate = it.checkIn.toLocalDate(),
                    checkInTime = it.checkIn.toLocalTime(),
                    checkOutDate = it.checkOut.toLocalDate(),
                    checkOutTime = it.checkOut.toLocalTime(),
                    documents = documents.map { document ->
                        SelectedDocument(
                            name = document.name,
                            path = document.path
                        )
                    }
                )
            } ?: AddAccommodationState()
        )
    }

    fun onSaveAccommodation() {
        val checkInDate = state.checkInDate
        val checkInTime = state.checkInTime
        val checkOutDate = state.checkOutDate
        val checkOutTime = state.checkOutTime

        if (
            checkInDate == null ||
            checkInTime == null ||
            checkOutDate == null ||
            checkOutTime == null
        ) {
            return
        }

        val checkIn = LocalDateTime.of(
            checkInDate,
            checkInTime
        )

        val checkOut = LocalDateTime.of(
            checkOutDate,
            checkOutTime
        )

        val accommodation = Accommodation(
            id = accommodation?.id ?: 0,
            tripId = tripId,
            name = state.name.trim(),
            address = state.address.trim(),
            contactPhone = state.contactPhone.trim().takeIf { it.isNotBlank() },
            checkIn = checkIn,
            checkOut = checkOut
        )

        if (isEditMode) {
            viewModel.updateAccommodation(
                accommodation = accommodation,
                documents = state.documents
            )
        } else {
            viewModel.addAccommodation(
                accommodation = accommodation,
                documents = state.documents
            )
        }

        onBackClick()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEditMode) {
                    stringResource(
                        R.string.edit_accommodation_title
                    )
                } else {
                    stringResource(
                        R.string.add_accommodation_title
                    )
                },
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AddAccommodationInfoSection(
                name = state.name,
                onNameChanged = {
                    state = state.copy(
                        name = it
                    )
                },
                address = state.address,
                onAddressChanged = {
                    state = state.copy(
                        address = it
                    )
                },
                contactPhone = state.contactPhone,
                onContactPhoneChanged = {
                    state = state.copy(
                        contactPhone = it.filter { char ->
                            char.isDigit() || char == '+'
                        }
                    )
                }
            )

            StayDateTimeSection(
                title = stringResource(
                    R.string.add_accommodation_check_in_section_title
                ),
                selectedDate = state.checkInDate,
                onDateSelected = {
                    state = state.copy(
                        checkInDate = it
                    )
                },
                selectedTime = state.checkInTime,
                onTimeSelected = {
                    state = state.copy(
                        checkInTime = it
                    )
                }
            )

            StayDateTimeSection(
                title = stringResource(
                    R.string.add_accommodation_check_out_section_title
                ),
                minDate = state.checkInDate,
                selectedDate = state.checkOutDate,
                onDateSelected = {
                    state = state.copy(
                        checkOutDate = it
                    )
                },
                selectedTime = state.checkOutTime,
                onTimeSelected = {
                    state = state.copy(
                        checkOutTime = it
                    )
                }
            )

            AccommodationDocumentsSection(
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
                enabled = state.isValid,
                onClick = ::onSaveAccommodation,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isEditMode) {
                        stringResource(
                            R.string.common_save_changes
                        )
                    } else {
                        stringResource(
                            R.string.add_accommodation_btn_label
                        )
                    }
                )
            }
        }
    }
}

data class AddAccommodationState(
    val name: String = "",
    val address: String = "",
    val contactPhone: String = "",
    val checkInDate: LocalDate? = null,
    val checkInTime: LocalTime? = null,
    val checkOutDate: LocalDate? = null,
    val checkOutTime: LocalTime? = null,
    val documents: List<SelectedDocument> = emptyList()
) {
    val isValid: Boolean
        get() =
            name.trim().isNotBlank() &&
                    address.trim().isNotBlank() &&
                    checkInDate != null &&
                    checkInTime != null &&
                    checkOutDate != null &&
                    checkOutTime != null
}