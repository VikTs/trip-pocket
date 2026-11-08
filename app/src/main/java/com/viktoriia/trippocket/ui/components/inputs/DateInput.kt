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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.utils.formatFullDate
import java.time.LocalDate

@Composable
fun DateInput(
    label: @Composable () -> Unit,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    moveFocusAfterSelection: Boolean = true
) {
    val context = LocalContext.current
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var shouldMoveFocus by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(showDatePicker, shouldMoveFocus) {
        if (!showDatePicker && shouldMoveFocus) {
            withFrameNanos {}

            focusManager.moveFocus(FocusDirection.Next)
            shouldMoveFocus = false
        }
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedDate?.let {
                formatFullDate(context, it)
            } ?: "",
            onValueChange = {},
            label = label,
            placeholder = {
                Text(stringResource(R.string.date_input_placeholder))
            },
            modifier = modifier
                .focusRequester(focusRequester),
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    focusRequester.requestFocus()
                    showDatePicker = true
                }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            selectedDate = selectedDate,
            minDate = minDate,
            maxDate = maxDate,
            onDateSelected = { date ->
                onDateSelected(date)
                shouldMoveFocus = moveFocusAfterSelection
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}