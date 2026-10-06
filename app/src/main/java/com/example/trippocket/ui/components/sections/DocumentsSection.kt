package com.example.trippocket.ui.components.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.R
import com.example.trippocket.ui.components.DocumentPreview
import com.example.trippocket.ui.components.inputs.SelectedDocument
import com.example.trippocket.utils.openFile

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DocumentsSection(
    title: String,
    documents: List<SelectedDocument>,
    onAddDocument: () -> Unit,
) {
    val context = LocalContext.current

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(
        modifier = Modifier.height(18.dp)
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        OutlinedIconButton(
            onClick = onAddDocument,
            modifier = Modifier.size(110.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(
                    R.string.upload_btn_label
                ),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}