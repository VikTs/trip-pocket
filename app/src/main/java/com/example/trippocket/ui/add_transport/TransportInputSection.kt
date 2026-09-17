package com.example.trippocket.ui.add_transport

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

@Composable
fun TransportInputSection(
    transportType: TransportType,
    onTransportTypeChange: (TransportType) -> Unit,
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
        label = "Type",
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

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}