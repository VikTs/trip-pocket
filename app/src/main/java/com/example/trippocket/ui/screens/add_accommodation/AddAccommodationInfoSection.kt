package com.example.trippocket.ui.screens.add_accommodation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R

@Composable
fun AddAccommodationInfoSection(
    name: String,
    address: String,
    contactPhone: String,
    onNameChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onContactPhoneChanged: (String) -> Unit,
) {
    Text(
        text = stringResource(
            R.string.add_accommodation_general_section_title
        ),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        label = {
            Text(
                stringResource(
                    R.string.add_accommodation_name_label
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    OutlinedTextField(
        value = address,
        onValueChange = onAddressChanged,
        label = {
            Text(
                stringResource(
                    R.string.add_accommodation_address_label
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    OutlinedTextField(
        value = contactPhone,
        onValueChange = onContactPhoneChanged,
        label = {
            Text(
                stringResource(
                    R.string.add_accommodation_contact_phone_label
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}