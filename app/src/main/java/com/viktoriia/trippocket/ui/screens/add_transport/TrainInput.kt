package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R

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
            Text(stringResource(R.string.add_transport_train_number_label))
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
                Text(stringResource(R.string.add_transport_coach_label))
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
                Text(stringResource(R.string.add_transport_place_label))
            },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}