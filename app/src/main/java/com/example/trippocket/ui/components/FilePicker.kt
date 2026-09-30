package com.example.trippocket.ui.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.trippocket.utils.createFilePickerIntent
import com.example.trippocket.utils.handlePickedFile

@Composable
fun rememberFilePicker(
    context: Context,
    onFileSelected: (SelectedDocument) -> Unit
): () -> Unit {

    var selectedPath by remember {
        mutableStateOf<String?>(null)
    }

    var selectedName by remember {
        mutableStateOf<String?>(null)
    }

    val filePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->

            val uri = result.data?.data
                ?: return@rememberLauncherForActivityResult

            selectedPath = null
            selectedName = null

            handlePickedFile(
                context = context,
                uri = uri,
                onPathChange = { path ->
                    selectedPath = path

                    if (selectedName != null) {
                        onFileSelected(
                            SelectedDocument(
                                name = selectedName!!,
                                path = path
                            )
                        )

                        selectedPath = null
                        selectedName = null
                    }
                },
                onNameChange = { name ->
                    selectedName = name

                    if (selectedPath != null && name != null) {
                        onFileSelected(
                            SelectedDocument(
                                name = name,
                                path = selectedPath!!
                            )
                        )

                        selectedPath = null
                        selectedName = null
                    }
                }
            )
        }

    return {
        filePickerLauncher.launch(
            createFilePickerIntent()
        )
    }
}