package com.viktoriia.trippocket.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.viktoriia.trippocket.R

@Composable
fun TruncatedAddressLink(
    address: String,
    additionalInfo: String?,
    maxLines: Int? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    val fullAddress = if (additionalInfo.isNullOrBlank()) {
        address
    } else {
        "$address, $additionalInfo"
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Text(
                text = address,
                maxLines = maxLines ?: Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable {
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

        Icon(
            imageVector = Icons.Outlined.ContentCopy,
            contentDescription = "Copy address",
            modifier = Modifier
                .padding(start = 4.dp)
                .size(14.dp)
                .clickable {
                    clipboard.setText(
                        AnnotatedString(address)
                    )

                    Toast.makeText(
                        context,
                        context.getString(
                            R.string.address_copied_message
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                },
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}