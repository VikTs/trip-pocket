package com.example.trippocket.ui.components.inputs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime?.hour ?: LocalTime.now().hour,
        initialMinute = selectedTime?.minute ?: LocalTime.now().minute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(
                        LocalTime.of(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                    )
                }
            ) {
                Text(stringResource(R.string.common_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.common_cancel))
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.padding(8.dp)
            )
        }
    )
}