package com.viktoriia.trippocket.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import java.io.File
import kotlin.io.copyTo
import kotlin.io.extension
import kotlin.io.outputStream
import kotlin.use

private const val PREVIEW_MAX_SIZE = 440

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

fun createFilePickerIntent(
    fileTypes: Array<String>? = null
): Intent {
    val types = fileTypes ?: arrayOf(
        "application/pdf",
        "image/*"
    )

    return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*"
        putExtra(
            Intent.EXTRA_MIME_TYPES,
            types
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

    if (!file.exists()) return null

    return when (file.extension.lowercase()) {
        "pdf" -> loadPdfPreview(file)
        else -> loadImagePreview(file)
    }
}

private fun loadImagePreview(file: File): Bitmap? {
    val bounds = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    BitmapFactory.decodeFile(file.absolutePath, bounds)

    if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
        return null
    }

    var sampleSize = 1
    val maxDimension = maxOf(bounds.outWidth, bounds.outHeight)

    while (maxDimension / (sampleSize * 2) >= PREVIEW_MAX_SIZE) {
        sampleSize *= 2
    }

    val options = BitmapFactory.Options().apply {
        inSampleSize = sampleSize
        inPreferredConfig = Bitmap.Config.RGB_565
    }

    return BitmapFactory.decodeFile(file.absolutePath, options)
}

private fun loadPdfPreview(file: File): Bitmap? {
    val descriptor = ParcelFileDescriptor.open(
        file,
        ParcelFileDescriptor.MODE_READ_ONLY
    )

    descriptor.use {
        PdfRenderer(it).use { renderer ->
            if (renderer.pageCount == 0) return null

            renderer.openPage(0).use { page ->
                val scale = minOf(
                    1f,
                    PREVIEW_MAX_SIZE.toFloat() /
                            maxOf(page.width, page.height)
                )

                val width = (page.width * scale)
                    .toInt()
                    .coerceAtLeast(1)

                val height = (page.height * scale)
                    .toInt()
                    .coerceAtLeast(1)

                val bitmap = Bitmap.createBitmap(
                    width,
                    height,
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