package com.viktoriia.trippocket.ui.screens.add_accommodation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.DateInput
import com.viktoriia.trippocket.ui.components.inputs.TimeInput
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun StayDateTimeSection(
    selectedDate: LocalDate?,
    title: String,
    onDateSelected: (LocalDate) -> Unit,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    minDate: LocalDate? = null,
    ) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DateInput(
            label = stringResource(R.string.add_accommodation_date_label),
            minDate = minDate,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            modifier = Modifier.weight(2f)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        TimeInput(
            label = stringResource(R.string.add_accommodation_time_label),
            selectedTime = selectedTime,
            onTimeSelected = onTimeSelected,
            modifier = Modifier.weight(1f)
        )
    }
}