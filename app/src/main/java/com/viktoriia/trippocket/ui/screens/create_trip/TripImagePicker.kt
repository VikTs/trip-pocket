package com.viktoriia.trippocket.ui.screens.create_trip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.components.DocumentPreview
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument

@Composable
fun TripImagePicker(
    selectedImage: SelectedDocument?,
    onPickImage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        selectedImage?.let { image ->
            DocumentPreview(
                path = image.path,
                fileName = "",
                onClick = onPickImage
            )
        }

        Button(onClick = onPickImage) {
            Text(
                text = if (selectedImage != null) {
                    stringResource(R.string.upload_another_image_btn_label)
                } else {
                    "+ ${stringResource(R.string.upload_image_btn_label)}"
                }
            )
        }
    }
}