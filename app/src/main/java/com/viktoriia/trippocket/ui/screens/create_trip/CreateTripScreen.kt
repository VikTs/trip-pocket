package com.viktoriia.trippocket.ui.screens.create_trip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.ui.components.inputs.DateInput
import com.viktoriia.trippocket.ui.components.TopBar
import java.time.LocalDate
import com.viktoriia.trippocket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    editTrip: Trip? = null,
    onTripSaved: (Trip) -> Unit,
    onBackClick: () -> Unit,
) {
    val isEditMode = editTrip != null
    var tripName by remember(editTrip?.id) {
        mutableStateOf(editTrip?.name ?: "")
    }

    var startDate by remember(editTrip?.id) {
        mutableStateOf<LocalDate?>(editTrip?.startDate)
    }

    var endDate by remember(editTrip?.id) {
        mutableStateOf<LocalDate?>(editTrip?.endDate)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEditMode)
                    stringResource(R.string.edit_trip_title)
                else stringResource(
                    R.string.create_trip_title
                ),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            OutlinedTextField(
                value = tripName,
                onValueChange = { newValue ->
                    tripName = newValue
                },
                label = {
                    Text(stringResource(R.string.create_trip_name_label))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            DateInput(
                label = stringResource(R.string.create_trip_start_date_label),
                selectedDate = startDate,
                onDateSelected = { date ->
                    startDate = date

                    if (endDate?.isBefore(date) == true) {
                        endDate = null
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DateInput(
                label = stringResource(R.string.create_trip_end_date_label),
                selectedDate = endDate,
                minDate = startDate,
                onDateSelected = { date ->
                    endDate = date
                }
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    val selectedStartDate =
                        startDate ?: return@Button

                    val selectedEndDate =
                        endDate ?: return@Button

                    val trip = Trip(
                        id = editTrip?.id ?: 0,
                        name = tripName,
                        startDate = selectedStartDate,
                        endDate = selectedEndDate
                    )

                    onTripSaved(trip)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = tripName.isNotBlank() &&
                        startDate != null &&
                        endDate != null
            ) {
                Text(
                    if (isEditMode) stringResource(R.string.common_save_changes)
                    else stringResource(R.string.create_trip_btn_label)
                )
            }
        }
    }
}