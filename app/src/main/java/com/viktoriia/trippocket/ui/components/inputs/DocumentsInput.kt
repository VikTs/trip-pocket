package com.viktoriia.trippocket.ui.components.inputs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.viktoriia.trippocket.R

@Composable
fun DocumentsInput(
    context: Context,
    documents: List<SelectedDocument>,
    onAddDocument: (SelectedDocument) -> Unit,
    onRemoveDocument: (SelectedDocument) -> Unit
) {
    val pickFile = rememberFilePicker(
        context = context,
        onFileSelected = onAddDocument
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        documents.forEach { document ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        onRemoveDocument(document)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }

    Button(
        onClick = pickFile
    ) {
        Text(
            "+ ${
                stringResource(
                    R.string.upload_btn_label
                )
            }"
        )
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}

data class SelectedDocument(
    val name: String,
    val path: String
)