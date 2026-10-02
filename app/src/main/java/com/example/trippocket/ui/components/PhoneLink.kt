package com.example.trippocket.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri

@Composable
fun PhoneLink(
    label: String,
    phone: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Text(
        text = buildAnnotatedString {
            append("$label: ")

            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(phone ?: "-")
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.clickable {
            phone
                ?.takeIf { it.isNotBlank() }
                ?.let { value ->
                    val intent = Intent(
                        Intent.ACTION_DIAL,
                        "tel:$value".toUri()
                    )

                    context.startActivity(intent)
                }
        }
    )
}