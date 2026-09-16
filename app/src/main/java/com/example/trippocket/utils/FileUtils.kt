package com.example.trippocket.utils

import android.content.Context
import android.net.Uri
import android.content.Intent
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import java.io.File

fun copyFileToInternalStorage(
    context: Context,
    uri: Uri
): String {

    val ticketsDirectory = File(
        context.filesDir,
        "tickets"
    )

    ticketsDirectory.mkdirs()

    val mimeType = context.contentResolver.getType(uri)

    val extension = when (mimeType) {
        "application/pdf" -> ".pdf"
        "image/jpeg" -> ".jpg"
        "image/png" -> ".png"
        else -> ""
    }

    val destinationFile = File(
        ticketsDirectory,
        "ticket_${System.currentTimeMillis()}$extension"
    )

    context.contentResolver
        .openInputStream(uri)
        ?.use { input ->
            destinationFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        ?: error("Could not open selected file")

    return destinationFile.absolutePath
}

fun openTicket(
    context: Context,
    path: String
) {
    val file = File(path)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val mimeType = when (file.extension.lowercase()) {
        "pdf" -> "application/pdf"
        "jpg", "jpeg", "png", "webp" -> "image/*"
        else -> "*/*"
    }

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(intent)
}

fun getFileName(
    context: Context,
    uri: Uri
): String? {
    val cursor = context.contentResolver.query(
        uri,
        arrayOf(OpenableColumns.DISPLAY_NAME),
        null,
        null,
        null
    )

    return cursor?.use {
        if (it.moveToFirst()) {
            it.getString(
                it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            )
        } else {
            null
        }
    }
}