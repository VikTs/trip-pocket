package com.example.trippocket.ui.screens.add_transport

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.utils.createFilePickerIntent
import com.example.trippocket.utils.handlePickedFile
import com.example.trippocket.R

@Composable
fun TicketInputSection(
    context: Context,
    documentName: String?,
    onDocumentPathChange: (String) -> Unit,
    onDocumentNameChange: (String?) -> Unit,
) {
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
            ?: return@rememberLauncherForActivityResult

        handlePickedFile(
            context = context,
            uri = uri,
            onPathChange = onDocumentPathChange,
            onNameChange = onDocumentNameChange
        )
    }

    Text(
        text = stringResource(R.string.add_transport_ticket_section_title),
        style = MaterialTheme.typography.titleMedium
    )

    documentName?.let { name ->
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Button(
        onClick = {
            val intent = createFilePickerIntent()
            filePickerLauncher.launch(intent)
        }
    ) {
        Text("+ ${stringResource(R.string.add_transport_ticket_upload_label)}")
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}