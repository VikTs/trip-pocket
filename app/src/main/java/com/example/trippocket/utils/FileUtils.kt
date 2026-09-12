package com.example.trippocket.utils

import android.content.Context
import android.net.Uri
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