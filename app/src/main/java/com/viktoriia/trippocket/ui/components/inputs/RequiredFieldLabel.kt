package com.viktoriia.trippocket.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun RequiredFieldLabel(
    text: String,
) {
    Text(
        text = buildAnnotatedString {
            append(text)
            withStyle(
                SpanStyle(color = MaterialTheme.colorScheme.error)
            ) {
                append(" *")
            }
        }
    )
}
