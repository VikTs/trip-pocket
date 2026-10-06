package com.example.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.data.model.AccommodationDocument
import com.example.trippocket.ui.components.inputs.SelectedDocument
import com.example.trippocket.ui.components.sections.DocumentsSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccommodationDocumentsSection(
    documents: List<AccommodationDocument>,
    onAddDocument: () -> Unit,
) {
    DocumentsSection(
        title = stringResource(
            R.string.accommodation_details_documents_section_title
        ),
        documents = documents.map {
            SelectedDocument(
                name = it.name,
                path = it.path
            )
        },
        onAddDocument = onAddDocument
    )
}