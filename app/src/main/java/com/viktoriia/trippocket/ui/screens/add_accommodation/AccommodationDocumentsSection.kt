package com.viktoriia.trippocket.ui.screens.add_accommodation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.inputs.DocumentsInput
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument
import com.viktoriia.trippocket.ui.components.sections.Accordion

@Composable
fun AccommodationDocumentsSection(
    documents: List<SelectedDocument>,
    onAddDocument: (SelectedDocument) -> Unit,
    onRemoveDocument: (SelectedDocument) -> Unit
) {
    val context = LocalContext.current

    Accordion(
        title = stringResource(
            R.string.add_accommodation_documents_section_title
        )
    ) {
        DocumentsInput(
            context = context,
            documents = documents,
            onAddDocument = onAddDocument,
            onRemoveDocument = onRemoveDocument
        )
    }
}