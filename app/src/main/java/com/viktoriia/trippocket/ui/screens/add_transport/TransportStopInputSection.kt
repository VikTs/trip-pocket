package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.ui.components.inputs.DateInput
import com.viktoriia.trippocket.ui.components.inputs.TimeInput
import java.time.LocalDate
import java.time.LocalTime
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.TransportType
import com.viktoriia.trippocket.ui.components.inputs.RequiredFieldLabel

@Composable
fun TransportStopInputSection(
    title: String,
    transportType: TransportType,
    city: String,
    date: LocalDate?,
    time: LocalTime?,
    address: String?,
    platform: String?,
    terminal: String?,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onCityChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onAddressChange: (String) -> Unit,
    onPlatformChange: (String) -> Unit,
    onTerminalChange: (String) -> Unit,
    isDepartureStop: Boolean = false,
    ) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = city,
        onValueChange = onCityChange,
        label = {
            RequiredFieldLabel(stringResource(R.string.add_transport_city_label))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        DateInput(
            label = {
                RequiredFieldLabel(stringResource(R.string.add_transport_date_label))
            },
            selectedDate = date,
            onDateSelected = onDateChange,
            minDate = minDate,
            maxDate = maxDate,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        TimeInput(
            label = {
                RequiredFieldLabel(stringResource(R.string.add_transport_time_label))
            },
            selectedTime = time,
            onTimeSelected = onTimeChange,
            modifier = Modifier.weight(1f)
        )
    }

    when (transportType) {
        TransportType.PLANE -> PlaneStopInput(
            isDepartureStop = isDepartureStop,
            address = address,
            terminal = terminal,
            platform = platform,
            onAddressChange = onAddressChange,
            onTerminalChange = onTerminalChange,
            onPlatformChange = onPlatformChange
        )

        else -> GroundTransportStopInput(
            address = address,
            platform = platform,
            onAddressChange = onAddressChange,
            onPlatformChange = onPlatformChange
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun PlaneStopInput(
    isDepartureStop: Boolean,
    address: String?,
    terminal: String?,
    platform: String?,
    onAddressChange: (String) -> Unit,
    onTerminalChange: (String) -> Unit,
    onPlatformChange: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = address ?: "",
            onValueChange = onAddressChange,
            label = {
                RequiredFieldLabel(
                    stringResource(R.string.add_transport_airport_label)
                )
            },
            singleLine = true,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = terminal ?: "",
            onValueChange = onTerminalChange,
            label = {
                Text(stringResource(R.string.add_transport_terminal_label))
            },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }

    if (isDepartureStop) {
        OutlinedTextField(
            value = platform ?: "",
            onValueChange = onPlatformChange,
            label = {
                Text(stringResource(R.string.add_transport_gate_label))
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GroundTransportStopInput(
    address: String?,
    platform: String?,
    onAddressChange: (String) -> Unit,
    onPlatformChange: (String) -> Unit
) {
    OutlinedTextField(
        value = address ?: "",
        onValueChange = onAddressChange,
        label = {
            RequiredFieldLabel(
                stringResource(R.string.add_transport_address_label)
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = platform ?: "",
        onValueChange = onPlatformChange,
        label = {
            Text(stringResource(R.string.add_transport_platform_label))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}