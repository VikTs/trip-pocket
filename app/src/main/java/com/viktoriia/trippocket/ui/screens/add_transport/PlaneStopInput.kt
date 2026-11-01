package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.RequiredFieldLabel
import com.viktoriia.trippocket.ui.components.inputs.SearchableDropdownField
import com.viktoriia.trippocket.utils.loadJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlaneStopInput(
    isDepartureStop: Boolean,
    address: String?,
    code: String?,
    terminal: String?,
    platform: String?,
    onAddressChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onTerminalChange: (String) -> Unit,
    onPlatformChange: (String) -> Unit
) {
    val context = LocalContext.current

    val airports = produceState<List<Airport>>(emptyList(), context) {
        value = withContext(Dispatchers.IO) {
            loadJson(context, "airports.json") { item ->
                Airport(
                    iataCode = item.getString("iata_code"),
                    name = item.getString("name")
                )
            }
        }
    }.value

    SearchableDropdownField(
        value = code ?: "",
        items = airports,
        onValueChange = onCodeChange,
        onItemSelected = { airport ->
            onCodeChange(airport.iataCode)
            onAddressChange(airport.name)
        },
        filter = { airport, query ->
            airport.iataCode.contains(query, ignoreCase = true) ||
                    airport.name.contains(query, ignoreCase = true)
        },
        itemContent = { airport ->
            Column {
                Text(
                    text = airport.iataCode,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = airport.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        label = { Text(stringResource(R.string.add_transport_airport_code_label)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters
        )
    )

    OutlinedTextField(
        value = address ?: "",
        onValueChange = onAddressChange,
        label = {
            RequiredFieldLabel(stringResource(R.string.add_transport_airport_name_label))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = terminal ?: "",
            onValueChange = onTerminalChange,
            label = {
                Text(stringResource(R.string.add_transport_terminal_label))
            },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        if (isDepartureStop) {
            OutlinedTextField(
                value = platform ?: "",
                onValueChange = onPlatformChange,
                label = {
                    Text(stringResource(R.string.add_transport_gate_label))
                },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

data class Airport(
    val iataCode: String,
    val name: String
)
