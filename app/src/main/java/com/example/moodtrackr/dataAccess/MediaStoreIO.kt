package com.example.moodtrackr.dataAccess

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
    @RequiresApi(Build.VERSION_CODES.R)
    override fun write(context: Context, path: String?, fileName: String, data: String): Boolean {
        try {
            val extension = fileName.substringAfterLast(".")
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, getMimeTypeFromExtension(extension))
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(fileName)

            val uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI

            val cursor = contentResolver.query(uri, null, selection, selectionArgs, null)

            if (cursor != null && cursor.moveToFirst()) {
                val fileIdColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val fileId = cursor.getLong(fileIdColumn)

                val fileUri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL, fileId)

                val outputStream: OutputStream? = contentResolver.openOutputStream(fileUri)
                outputStream?.use { it.write(data.toByteArray()) }
                cursor.close()
                return true
            } else {
                val newUri = contentResolver.insert(uri, contentValues)

                if (newUri != null) {
                    val outputStream: OutputStream? = contentResolver.openOutputStream(newUri)
                    outputStream?.use { it.write(data.toByteArray()) }
                    return true
                }
            }
        } catch (ex: Exception) {
            Log.e("MediaStoreIO.write", ex.stackTraceToString())
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun read(context: Context, path: String?, fileName: String): String? {
        try {
            val contentResolver = context.contentResolver
            val uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(fileName)

            val cursor = contentResolver.query(uri, null, selection, selectionArgs, null)

            if (cursor != null && cursor.moveToFirst()) {
                val fileIdColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val fileId = cursor.getLong(fileIdColumn)

                val fileUri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL, fileId)

                var inputStream: InputStream? = null

                try {
                    inputStream = contentResolver.openInputStream(fileUri)
                    return inputStream?.bufferedReader().use { it?.readText() }
                } catch (ex: Exception) {
                    Log.e("MediaStoreIO.read", ex.stackTraceToString())
                } finally {
                    inputStream?.close()
                }
            } else {
                Log.d("MediaStoreIO.read", "File not found: $fileName")
            }

            cursor?.close()
        } catch (ex: Exception) {
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