package com.viktoriia.trippocket.ui.components.inputs

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.viktoriia.trippocket.utils.copyFileToInternalStorage
import com.viktoriia.trippocket.utils.createFilePickerIntent
import com.viktoriia.trippocket.utils.getFileName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun rememberFilePicker(
    context: Context,
    onFileSelected: (SelectedDocument) -> Unit,
    fileTypes: Array<String>? = null,
): () -> Unit {
    val scope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
            ?: return@rememberLauncherForActivityResult

        if (isProcessing) return@rememberLauncherForActivityResult

        scope.launch {
            isProcessing = true

            try {
                val document = withContext(Dispatchers.IO) {
                    prepareSelectedDocument(context, uri)
                }

                onFileSelected(document)
            } catch (e: Exception) {
                Log.e("TripPocket", "Failed to process selected document", e)
            } finally {
                isProcessing = false
            }
        }
    }

    return {
        if (!isProcessing) {
            filePickerLauncher.launch(createFilePickerIntent(fileTypes))
        }
    }
}

private fun prepareSelectedDocument(
    context: Context,
    uri: Uri
): SelectedDocument {
    val originalName = getFileName(context, uri) ?: "ticket"
    val baseName = originalName.substringBeforeLast(
        '.',
        originalName
    ).ifBlank { "ticket" }

    val uniqueName = "${baseName}_${System.currentTimeMillis()}"

    val path = copyFileToInternalStorage(
        context = context,
        uri = uri,
        fileName = uniqueName
    )

    return SelectedDocument(
        name = originalName,
        path = path
    )
}