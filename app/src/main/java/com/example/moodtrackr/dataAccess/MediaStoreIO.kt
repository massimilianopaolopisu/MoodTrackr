package com.example.moodtrackr.dataAccess

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import java.io.InputStream
import java.io.OutputStream

class MediaStoreIO : IFileSystemIO {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun write(context: Context, path: String?, fileName: String, data: String): Boolean {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, path?: Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            try {
                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream?.use { it.write(data.toByteArray()) }
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun read(context: Context, path: String?, fileName: String): String? {
        val contentResolver = context.contentResolver
        val projection = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DISPLAY_NAME)
        val selection = "${MediaStore.MediaColumns.RELATIVE_PATH} = ? AND ${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(path ?: Environment.DIRECTORY_DOWNLOADS, fileName)

        val uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI

        contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val fileId = cursor.getLong(columnIndex)

                val fileUri = ContentUris.withAppendedId(uri, fileId)

                var inputStream: InputStream? = null

                try {
                    inputStream = contentResolver.openInputStream(fileUri)
                    return inputStream?.bufferedReader().use { it?.readText() }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    inputStream?.close()
                }
            }
        }

        return null
    }
}