package com.example.trippocket.ui.add_transport

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.TransportStop
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.model.TransportType
import com.example.trippocket.ui.components.DateInput
import com.example.trippocket.ui.components.Dropdown
import com.example.trippocket.ui.components.TimeInput
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.utils.copyFileToInternalStorage
import com.example.trippocket.viewmodel.TripDetailsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    tripId: Long,
    onBackClick: () -> Unit,
    viewModel: TripDetailsViewModel,
) {
    val context = LocalContext.current
    var selectedType by remember {
        mutableStateOf(TransportType.BUS)
    }

    var typeExpanded by remember {
        mutableStateOf(false)
    }

    var fromCity by remember {
        mutableStateOf("")
    }

    var fromDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var fromTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    var toCity by remember {
        mutableStateOf("")
    }

    var toDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var toTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    var documentPath by remember {
        mutableStateOf<String?>(null)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: return@rememberLauncherForActivityResult

        documentPath = copyFileToInternalStorage(
            context = context,
            uri = uri
        )
    }

    Scaffold(
        topBar = {
            TopBar(title = "Add transport", onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Transport",
                style = MaterialTheme.typography.titleMedium
            )

            Dropdown(
                selectedItem = selectedType,
                items = TransportType.entries,
                label = "Type",
                itemText = { type ->
                    when (type) {
                        TransportType.BUS -> "Bus"
                        TransportType.TRAIN -> "Train"
                    }
                },
                expanded = typeExpanded,
                onExpandedChange = {
                    typeExpanded = it
                },
                onItemSelected = {
                    selectedType = it
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "From",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = fromCity,
                onValueChange = { fromCity = it },
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
                    selectedDate = fromDate,
                    onDateSelected = { date ->
                        fromDate = date

                        if (toDate?.isBefore(date) == true) {
                            toDate = null
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                TimeInput(
                    label = "Time",
                    selectedTime = fromTime,
                    onTimeSelected = {
                        fromTime = it
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "To",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = toCity,
                onValueChange = { toCity = it },
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
                    selectedDate = toDate,
                    onDateSelected = { date ->
                        toDate = date
                    },
                    minDate = fromDate,
                    modifier = Modifier.weight(1f)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                TimeInput(
                    label = "Time",
                    selectedTime = toTime,
                    onTimeSelected = {
                        toTime = it
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Ticket",
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                        putExtra(
                            Intent.EXTRA_MIME_TYPES,
                            arrayOf(
                                "application/pdf",
                                "image/*"
                            )
                        )
                    }

                    filePickerLauncher.launch(intent);
                }
            ) {
                Text("+ Upload ticket")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = {
                    val ticket = TransportTicket(
                        tripId = tripId,
                        type = selectedType,
                        documentPath = documentPath,
                        from = TransportStop(
                            city = fromCity,
                            time = LocalDateTime.of(
                                fromDate,
                                fromTime
                            ),
                        ),
                        to = TransportStop(
                            city = toCity,
                            time = LocalDateTime.of(
                                toDate,
                                toTime
                            ),
                        )
                    ) ?: return@Button

                    viewModel.addTicket(ticket)
                    onBackClick()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = fromCity.isNotBlank() &&
                        toCity.isNotBlank() &&
                        fromDate != null &&
                        toDate != null &&
                        fromTime != null &&
                        toTime != null
            ) {
                Text("Add transport")
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}