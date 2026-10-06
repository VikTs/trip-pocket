package com.example.trippocket.ui.screens.add_accommodation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.ui.components.inputs.DocumentsInput
import com.example.trippocket.ui.components.inputs.SelectedDocument

@Composable
fun AccommodationDocumentsSection(
    documents: List<SelectedDocument>,
    onAddDocument: (SelectedDocument) -> Unit,
    onRemoveDocument: (SelectedDocument) -> Unit
) {
    val context = LocalContext.current

    Text(
        text = stringResource(
            R.string.add_accommodation_documents_section_title
        ),
        style = MaterialTheme.typography.titleMedium
    )

    DocumentsInput(
        context = context,
        documents = documents,
        onAddDocument = onAddDocument,
        onRemoveDocument = onRemoveDocument
    )
}