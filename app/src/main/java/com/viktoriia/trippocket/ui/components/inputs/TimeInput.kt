package com.viktoriia.trippocket.ui.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.ui.utils.formatTime
import java.time.LocalTime
import com.viktoriia.trippocket.R

@Composable
fun TimeInput(
    label: @Composable () -> Unit,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    moveFocusAfterSelection: Boolean = true,
    autoOpenOnFocus: Boolean = true
) {
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var shouldMoveFocus by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(showTimePicker, shouldMoveFocus) {
        if (!showTimePicker && shouldMoveFocus) {
            withFrameNanos {}

            focusManager.moveFocus(FocusDirection.Next)
            shouldMoveFocus = false
        }
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedTime?.let { formatTime(it) } ?: "",
            onValueChange = {},
            label = label,
            placeholder = {
                Text(stringResource(R.string.time_input_placeholder))
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (autoOpenOnFocus && it.isFocused) {
                        showTimePicker = true
                    }
                },
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    focusRequester.requestFocus()
                    showTimePicker = true
                }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            selectedTime = selectedTime,
            onTimeSelected = { time ->
                onTimeSelected(time)
                shouldMoveFocus = moveFocusAfterSelection
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            }
        )
    }
}
