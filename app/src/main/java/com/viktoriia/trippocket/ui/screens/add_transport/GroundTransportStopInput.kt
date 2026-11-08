package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.RequiredFieldLabel

@Composable
fun GroundTransportStopInput(
    address: String?,
    platform: String?,
    onAddressChange: (String) -> Unit,
    onPlatformChange: (String) -> Unit,
    isDepartureStop: Boolean
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
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )

    OutlinedTextField(
        value = platform ?: "",
        onValueChange = onPlatformChange,
        label = {
            Text(stringResource(R.string.add_transport_platform_label))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = if (isDepartureStop) KeyboardOptions(
            imeAction = ImeAction.Next
        ) else KeyboardOptions()
    )
}