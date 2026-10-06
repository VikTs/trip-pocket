package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.LabeledCheckbox

@Composable
fun PrivateBusInputSection(
    isPrivateBus: Boolean,
    carrier: String?,
    driverPhone: String?,
    onPrivateBusChange: (Boolean) -> Unit,
    onCarrierChange: (String?) -> Unit,
    onDriverPhoneChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LabeledCheckbox(
            checked = isPrivateBus,
            label = stringResource(
                R.string.add_transport_private_bus_label
            ),
            onCheckedChange = onPrivateBusChange
        )

        if (isPrivateBus) {
            OutlinedTextField(
                value = carrier ?: "",
                onValueChange = onCarrierChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        stringResource(
                            R.string.add_transport_carrier_label
                        )
                    )
                },
                singleLine = true
            )

            OutlinedTextField(
                value = driverPhone ?: "",
                onValueChange = onDriverPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        stringResource(
                            R.string.add_transport_driver_phone_label
                        )
                    )
                },
                singleLine = true
            )
        }
    }
}