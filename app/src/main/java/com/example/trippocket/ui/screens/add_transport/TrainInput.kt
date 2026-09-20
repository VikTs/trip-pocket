package com.example.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrainInput(
    transportNumber: String?,
    coach: String?,
    place: String?,
    onTransportNumberChange: (String?) -> Unit,
    onCoachChange: (String?) -> Unit,
    onPlaceChange: (String?) -> Unit,
) {
    OutlinedTextField(
        value = transportNumber ?: "",
        onValueChange = onTransportNumberChange,
        label = {
            Text("Train number*")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = coach ?: "",
            onValueChange = onCoachChange,
            label = {
                Text("Coach")
            },
            singleLine = true,
            modifier = Modifier.weight(1f)
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