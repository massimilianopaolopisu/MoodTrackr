package com.example.moodtrackr.dataAccess

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.anggrayudi.storage.media.MediaStoreCompat
import com.anggrayudi.storage.media.MediaType
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import java.io.InputStream
import java.io.OutputStream

class MediaStoreCompatIO : IFileSystemIO {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun write(context: Context, fileName: String, data: String): Boolean {
        try {
            val extension = fileName.substringAfterLast(".")
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, getMimeTypeFromExtension(extension))
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val existingFile = MediaStoreCompat.fromFileName(context, MediaType.DOWNLOADS, fileName)

            if (existingFile != null) {
                val uri = existingFile.uri

                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream?.use { it.write(data.toByteArray()) }
                return true
            } else {
                val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                if (uri != null) {
                    val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                    outputStream?.use { it.write(data.toByteArray()) }
                    return true
                }
            }
        } catch (ex: Exception)
        {
            Log.e("MediaStoreIO.write", ex.stackTraceToString())
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun read(context: Context, fileName: String): String? {
        try {
            val file = MediaStoreCompat.fromFileName(context, MediaType.DOWNLOADS, fileName)
            val contentResolver = context.contentResolver

            if (file?.uri != null) {
                val fileUri = file.uri
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