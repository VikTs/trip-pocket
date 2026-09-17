package com.example.trippocket.ui.add_transport

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun TransportInputSection(
    transportType: TransportType,
    transportNumber: String?,
    place: String?,
    onTransportTypeChange: (TransportType) -> Unit,
    onTransportNumberChange: (String?) -> Unit,
    onPlaceChange: (String?) -> Unit,
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
        itemText = { type ->
            when (type) {
                TransportType.BUS -> "Bus"
                TransportType.TRAIN -> "Train"
            }
        },
        expanded = typeExpanded,
        onExpandedChange = {
            typeExpanded = it
        },
        onItemSelected = onTransportTypeChange
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = transportNumber ?: "",
            onValueChange = onTransportNumberChange,
            label = {
                Text("Transport number")
            },
            singleLine = true,
            modifier = Modifier.weight(2f)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        OutlinedTextField(
            value = place ?: "",
            onValueChange = onPlaceChange,
            label = {
                Text("Place")
            },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}