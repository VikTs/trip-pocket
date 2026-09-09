package com.example.trippocket.ui.components

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
import com.example.trippocket.utils.formatTripTime
import java.time.LocalTime

@Composable
fun TimeInput(
    label: String,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedTime?.let { formatTripTime(it) } ?: "",
            onValueChange = {},
            label = {
                Text(label)
            },
            placeholder = {
                Text("Select time")
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    showTimePicker = true
                }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            selectedTime = selectedTime,
            onTimeSelected = { time ->
                onTimeSelected(time)
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            }
        )
    }
}
