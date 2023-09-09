package com.example.moodtrackr.dataAccess

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import java.io.InputStream
import java.io.OutputStream

class MediaStoreIO : IFileSystemIO {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun write(context: Context, path: String?, fileName: String, data: String): Boolean {
        try {
            val extension = fileName.substringAfterLast(".")
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, getMimeTypeFromExtension(extension))
                put(MediaStore.MediaColumns.RELATIVE_PATH, path ?: Environment.DIRECTORY_DOWNLOADS)
            }

            val uri =
                contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if (uri != null) {
                try {
                    val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                    outputStream?.use { it.write(data.toByteArray()) }
                    return true
                } catch (ex: Exception) {
                    Log.e("MediaStoreIO.write", ex.stackTraceToString())
                }
            }
        } catch (ex: Exception)
        {
            Log.e("MediaStoreIO.write", ex.stackTraceToString())
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun read(context: Context, path: String?, fileName: String): String? {
        try {
            val contentResolver = context.contentResolver
            val projection =
                arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DISPLAY_NAME)
            val selection =
                "${MediaStore.MediaColumns.RELATIVE_PATH} = ? AND ${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(path ?: Environment.DIRECTORY_DOWNLOADS, fileName)

            val uri = MediaStore.Downloads.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )

            contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                    val fileId = cursor.getLong(columnIndex)

                    val fileUri = ContentUris.withAppendedId(uri, fileId)

                    var inputStream: InputStream? = null

                    try {
                        inputStream = contentResolver.openInputStream(fileUri)
                        return inputStream?.bufferedReader().use { it?.readText() }
                    } catch (ex: Exception) {
                        Log.e("MediaStoreIO.read", ex.stackTraceToString())
                    } finally {
                        inputStream?.close()
                    }
                }
            }
        }
        catch (ex: Exception) {
            Log.e("MediaStoreIO.read", ex.stackTraceToString())
        }

        return null
    }
}

private fun getMimeTypeFromExtension(extension: String): String {
    return when (extension.lowercase()) {
        "json" -> "application/json"
        "txt" -> "text/plain"
        else -> "application/octet-stream"
    }
}