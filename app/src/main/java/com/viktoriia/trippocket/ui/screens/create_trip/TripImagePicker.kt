package com.viktoriia.trippocket.ui.screens.create_trip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                onClick = onPickImage
            )
        }

        Button(onClick = onPickImage) {
            if (selectedImage != null) {
                Icon(
                    imageVector = Icons.Outlined.Autorenew,
                    contentDescription = null,
                    modifier = Modifier.size(18.sp.value.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.sp.value.dp)
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = if (selectedImage != null) {
                    stringResource(R.string.upload_another_image_btn_label)
                } else {
                    stringResource(R.string.upload_image_btn_label)
                }
            )
        }
    }
}