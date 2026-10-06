package com.viktoriia.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.data.model.TransportDocument
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument
import com.viktoriia.trippocket.ui.components.sections.DocumentsSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransportDocumentsSection(
    documents: List<TransportDocument>,
    onAddDocument: () -> Unit,
) {
    DocumentsSection(
        title = stringResource(
            R.string.transport_details_documents_section_title
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