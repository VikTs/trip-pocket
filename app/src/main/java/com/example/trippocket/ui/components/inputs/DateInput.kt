package com.example.trippocket.ui.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.utils.formatTripDate
import java.time.LocalDate

@Composable
fun DateInput(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    minDate: LocalDate? = null
    ) {
    val context = LocalContext.current
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedDate?.let { formatTripDate(context, it) } ?: "",
            onValueChange = {},
            label = {
                Text(label)
            },
            placeholder = {
                Text(stringResource(R.string.date_input_placeholder))
            },
            modifier = modifier,
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    showDatePicker = true
                }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            selectedDate = selectedDate,
            minDate = minDate,
            onDateSelected = { date ->
                onDateSelected(date)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}