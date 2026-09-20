package com.example.trippocket.ui.screens.add_transport

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
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.Dropdown
import com.example.trippocket.ui.extensions.toDisplayName

@Composable
fun TransportInputSection(
    transportType: TransportType,
    transportNumber: String?,
    coach: String?,
    place: String?,
    onTransportTypeChange: (TransportType) -> Unit,
    onTransportNumberChange: (String?) -> Unit,
    onPlaceChange: (String?) -> Unit,
    onCoachChange: (String?) -> Unit,
) {
    var typeExpanded by remember {
        mutableStateOf(false)
    }

    Text(
        text = "Transport",
        style = MaterialTheme.typography.titleMedium
    )

    Dropdown(
        selectedItem = transportType,
        items = TransportType.entries,
        label = "Type*",
        itemText = {
            it.toDisplayName()
        },
        expanded = typeExpanded,
        onExpandedChange = {
            typeExpanded = it
        },
        onItemSelected = onTransportTypeChange
    )


    if (transportType == TransportType.TRAIN) {
        TrainInput(
            transportNumber,
            coach,
            place,
            onTransportNumberChange,
            onCoachChange,
            onPlaceChange
        )
    } else {
        BusInput(
            transportNumber, place, onTransportNumberChange, onPlaceChange
        )
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}
