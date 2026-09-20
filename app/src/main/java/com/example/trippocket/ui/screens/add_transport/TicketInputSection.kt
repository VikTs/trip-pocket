package com.example.trippocket.ui.screens.add_transport

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.utils.copyFileToInternalStorage
import com.example.trippocket.utils.getFileName


@Composable
fun TicketInputSection(
    context: Context,
    documentName: String?,
    onDocumentPathChange: (String) -> Unit,
    onDocumentNameChange: (String) -> Unit,
) {
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: return@rememberLauncherForActivityResult

        val path = copyFileToInternalStorage(
            context = context,
            uri = uri
        )

        val name = getFileName(
            context = context,
            uri = uri
        )

        onDocumentPathChange(path)
        if (name != null) {
            onDocumentNameChange(name)
        }
    }

    Text(
        text = "Ticket",
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
}