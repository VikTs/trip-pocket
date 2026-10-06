package com.viktoriia.trippocket.utils

import android.content.Context
import android.net.Uri
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import java.io.File
import kotlin.use

fun copyFileToInternalStorage(
    context: Context,
    uri: Uri,
    fileName: String
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
        "${fileName}$extension"
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

fun openFile(
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

fun createFilePickerIntent(): Intent {
    return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*"
        putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf(
                "application/pdf",
                "image/*"
            )
        )
    }
}

fun handlePickedFile(
    context: Context,
    uri: Uri,
    onPathChange: ((String) -> Unit),
    onNameChange: ((String?) -> Unit)? = null
) {
    val name = getFileName(
        context = context,
        uri = uri
    )

    val originalName = name ?: "ticket"
    val extension = originalName.substringAfterLast('.', "")
    val baseName = originalName.removeSuffix(".$extension")
    val fileName = "${baseName}_${System.currentTimeMillis()}.$extension"

    val path = copyFileToInternalStorage(
        context = context,
        uri = uri,
        fileName = fileName
    )

    onPathChange(path)
    onNameChange?.invoke(name)
}

fun loadFilePreview(path: String): Bitmap? {
    val file = File(path)

    if (!file.exists()) {
        return null
    }

    return when {
        file.extension.equals("pdf", ignoreCase = true) -> {
            loadPdfPreview(file)
        }

        else -> {
            BitmapFactory.decodeFile(file.absolutePath)
        }
    }
}

private fun loadPdfPreview(file: File): Bitmap? {
    val descriptor = ParcelFileDescriptor.open(
        file,
        ParcelFileDescriptor.MODE_READ_ONLY
    )

    descriptor.use {
        PdfRenderer(it).use { renderer ->
            if (renderer.pageCount == 0) {
                return null
            }

            renderer.openPage(0).use { page ->
                val scale = 2

                val bitmap = createBitmap(
                    page.width * scale,
                    page.height * scale,
                    Bitmap.Config.ARGB_8888
                )

                page.render(
                    bitmap,
                    null,
                    null,
                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                )

                return bitmap
            }
        }
    }
}