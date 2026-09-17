package com.example.trippocket.ui.add_transport

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.ui.components.DateInput
import com.example.trippocket.ui.components.TimeInput
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TransportStopInputSection(
    title: String,
    city: String,
    date: LocalDate?,
    time: LocalTime?,
    minDate: LocalDate?,
    onCityChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = city,
        onValueChange = onCityChange,
        label = {
            Text("City")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DateInput(
            label = "Date",
            selectedDate = date,
            onDateSelected = onDateChange,
            minDate = minDate,
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        TimeInput(
            label = "Time",
            selectedTime = time,
            onTimeSelected = onTimeChange,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}