package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.TransportType
import com.viktoriia.trippocket.ui.components.inputs.Dropdown
import com.viktoriia.trippocket.ui.components.inputs.RequiredFieldLabel

@Composable
fun TransportInputSection(
    transportType: TransportType,
    transportNumber: String?,
    coach: String?,
    place: String?,
    isPrivateBus: Boolean,
    carrier: String?,
    driverPhone: String?,
    onPrivateBusChange: (Boolean) -> Unit,
    onCarrierChange: (String?) -> Unit,
    onDriverPhoneChange: (String?) -> Unit,
    onTransportTypeChange: (TransportType) -> Unit,
    onTransportNumberChange: (String?) -> Unit,
    onPlaceChange: (String?) -> Unit,
    onCoachChange: (String?) -> Unit,
) {
    var typeExpanded by remember {
        mutableStateOf(false)
    }

    Text(
        text = stringResource(R.string.add_transport_transport_section_title),
        style = MaterialTheme.typography.titleMedium
    )

    Dropdown(
        selectedItem = transportType,
        items = TransportType.entries,
        label = {
            RequiredFieldLabel(
                text = stringResource(R.string.add_transport_type_label)
            )
        },
        itemText = {
            when (it) {
                TransportType.BUS ->
                    stringResource(R.string.transport_type_bus)

                TransportType.TRAIN ->
                    stringResource(R.string.transport_type_train)

                TransportType.PLANE ->
                    stringResource(R.string.transport_type_plane)
            }
        },
        expanded = typeExpanded,
        onExpandedChange = {
            typeExpanded = it
        },
        onItemSelected = onTransportTypeChange
    )

    when (transportType) {
        TransportType.TRAIN -> {
            TrainInput(
                transportNumber = transportNumber,
                coach = coach,
                place = place,
                onTransportNumberChange = onTransportNumberChange,
                onCoachChange = onCoachChange,
                onPlaceChange = onPlaceChange
            )
        }

        TransportType.BUS -> {
            BusInput(
                transportNumber = transportNumber,
                place = place,
                onTransportNumberChange = onTransportNumberChange,
                onPlaceChange = onPlaceChange
            )

            PrivateBusInputSection(
                isPrivateBus = isPrivateBus,
                carrier = carrier,
                driverPhone = driverPhone,
                onPrivateBusChange = onPrivateBusChange,
                onCarrierChange = onCarrierChange,
                onDriverPhoneChange = onDriverPhoneChange
            )
        }

        TransportType.PLANE -> {
            PlaneInput(
                transportNumber = transportNumber,
                carrier = carrier,
                place = place,
                onTransportNumberChange = onTransportNumberChange,
                onCarrierChange = onCarrierChange,
                onPlaceChange = onPlaceChange
            )
        }
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}
