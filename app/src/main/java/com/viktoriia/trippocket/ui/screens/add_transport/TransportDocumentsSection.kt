package com.viktoriia.trippocket.ui.screens.add_transport

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.DocumentsInput
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument

@Composable
fun TransportDocumentsSection(
    documents: List<SelectedDocument>,
    onAddDocument: (SelectedDocument) -> Unit,
    onRemoveDocument: (SelectedDocument) -> Unit
) {
    val context = LocalContext.current

    Text(
        text = stringResource(
            R.string.add_transport_documents_section_title
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