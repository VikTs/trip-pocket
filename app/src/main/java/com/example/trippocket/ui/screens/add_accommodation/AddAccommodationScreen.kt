package com.example.trippocket.ui.screens.add_accommodation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.ui.components.DateInput
import com.example.trippocket.ui.components.TimeInput
import com.example.trippocket.ui.components.TopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AddAccommodationScreen(
    tripId: Long,
    accommodation: Accommodation? = null,
    onBackClick: () -> Unit,
    onSaveClick: (Accommodation) -> Unit
) {
    val isEditing = accommodation != null

    var state by remember {
        mutableStateOf(
            AddAccommodationState(
                name = accommodation?.name.orEmpty(),
                address = accommodation?.address.orEmpty(),
                checkInDate = accommodation?.checkIn?.toLocalDate(),
                checkInTime = accommodation?.checkIn?.toLocalTime(),
                checkOutDate = accommodation?.checkOut?.toLocalDate(),
                checkOutTime = accommodation?.checkOut?.toLocalTime(),
            )
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEditing) {
                    "Edit accommodation"
                } else {
                    "Add accommodation"
                },
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(all = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Accommodation",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    state = state.copy(name = it)
                },
                label = {
                    Text("Name*")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.address,
                onValueChange = {
                    state = state.copy(address = it)
                },
                label = {
                    Text("Address")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Check-in",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DateInput(
                    label = "Date*",
                    selectedDate = state.checkInDate,
                    onDateSelected = {
                        state = state.copy(checkInDate = it)
                    },
                    modifier = Modifier.weight(2f)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                TimeInput(
                    label = "Time*",
                    selectedTime = state.checkInTime,
                    onTimeSelected = {
                        state = state.copy(checkInTime = it)
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Check-out",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DateInput(
                    label = "Date*",
                    minDate = state.checkInDate,
                    selectedDate = state.checkOutDate,
                    onDateSelected = {
                        state = state.copy(checkOutDate = it)
                    },
                    modifier = Modifier.weight(2f)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                TimeInput(
                    label = "Time*",
                    selectedTime = state.checkOutTime,
                    onTimeSelected = {
                        state = state.copy(checkOutTime = it)
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                enabled = state.isValid,
                onClick = {
                    val checkInDate =
                        state.checkInDate ?: return@Button

                    val checkInTime =
                        state.checkInTime ?: return@Button

                    val checkOutDate =
                        state.checkOutDate ?: return@Button

                    val checkOutTime =
                        state.checkOutTime ?: return@Button

                    val checkIn =
                        LocalDateTime.of(
                            checkInDate,
                            checkInTime
                        )

                    val checkOut =
                        LocalDateTime.of(
                            checkOutDate,
                            checkOutTime
                        )

                    onSaveClick(
                        Accommodation(
                            id = accommodation?.id ?: 0,
                            tripId = tripId,
                            name = state.name,
                            address = state.address
                                .ifBlank { null },
                            checkIn = checkIn,
                            checkOut = checkOut,
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isEditing) {
                        "Save changes"
                    } else {
                        "Add accommodation"
                    }
                )
            }
        }
    }
}

data class AddAccommodationState(
    val name: String = "",
    val address: String = "",
    val checkInDate: LocalDate? = null,
    val checkInTime: LocalTime? = null,
    val checkOutDate: LocalDate? = null,
    val checkOutTime: LocalTime? = null,
    val bookingNumber: String = "",
    val documentPath: String? = null
) {
    val isValid: Boolean
        get() =
            name.trim().isNotBlank() &&
                    checkInDate != null &&
                    checkInTime != null &&
                    checkOutDate != null &&
                    checkOutTime != null
}