package com.viktoriia.trippocket.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri

@Composable
fun PhoneLink(
    phone: String,
    label: String? = null,
    style: TextStyle? = null
) {
    val context = LocalContext.current

    Text(
        text = buildAnnotatedString {
            if (label != null) {
                append("$label: ")
            }

            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(phone)
            }
        },
        style = style ?: MaterialTheme.typography.bodyLarge,
        modifier = Modifier.clickable {
            if (phone.isNotBlank()) {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    "tel:$phone".toUri()
                )

                context.startActivity(intent)
            }
        }
    )
}