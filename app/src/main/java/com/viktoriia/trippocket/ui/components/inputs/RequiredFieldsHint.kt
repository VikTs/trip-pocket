package com.viktoriia.trippocket.ui.components.inputs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.viktoriia.trippocket.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun RequiredFieldsHint(
    modifier: Modifier = Modifier
) {
    val text = stringResource(R.string.required_fields_hint)
    val errorColor = MaterialTheme.colorScheme.error
    val starIndex = text.indexOf('*')

    val annotatedText = buildAnnotatedString {
        if (starIndex != -1) {
            append(text.substring(0, starIndex))

            withStyle(SpanStyle(color = errorColor)) {
                append("*")
            }

            append(text.substring(starIndex + 1))
        } else {
            append(text)
        }
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(2.dp))
}