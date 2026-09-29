package com.example.trippocket.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.platform.LocalContext
import com.example.trippocket.R

@Composable
fun AddressLink(
    address: String,
    additionalInfo: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    val copyIconId = "copy_icon"

    val fullAddress = if (additionalInfo.isNullOrBlank()) {
        address
    } else {
        "$address, $additionalInfo"
    }

    Text(
        text = buildAnnotatedString {
            append(address)
            appendInlineContent(copyIconId)
        },
        inlineContent = mapOf(
            copyIconId to InlineTextContent(
                placeholder = Placeholder(
                    width = 20.sp,
                    height = 14.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Copy address",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(12.dp)
                        .clickable {
                            clipboard.setText(
                                AnnotatedString(fullAddress)
                            )

                            Toast.makeText(
                                context,
                                context.getString(R.string.address_copied_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        ),
        modifier = modifier.clickable {
            val uri = "geo:0,0?q=${Uri.encode(fullAddress)}".toUri()

            context.startActivity(
                Intent(Intent.ACTION_VIEW, uri)
            )
        },
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 24.sp
        ),
        textDecoration = TextDecoration.Underline
    )
}