package com.example.trippocket.ui.screens.transport_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.data.model.TransportDocument
import com.example.trippocket.ui.components.DocumentPreview
import com.example.trippocket.utils.openFile
import kotlin.collections.forEach

@Composable
fun TransportDocumentsSection(documents: List<TransportDocument>) {
    val context = LocalContext.current

    Spacer(
        modifier = Modifier.height(40.dp)
    )

    Text(
        text = stringResource(
            R.string.transport_details_documents_section_title
        ),
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(
        modifier = Modifier.height(24.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        documents.forEach { document ->
            DocumentPreview(
                path = document.path,
                fileName = document.name,
                onClick = {
                    openFile(
                        context = context,
                        path = document.path
                    )
                }
            )
        }
    }
}