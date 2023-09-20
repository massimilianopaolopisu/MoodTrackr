package com.example.moodtrackr.dataAccess

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import com.example.moodtrackr.helpers.ActivityHelper
import java.io.*

class CustomFileSystemIO: IFileSystemIO {

    private val _defaultPath: String = "Download"
    private var _activity: Activity? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun write(context: Context, path: String?, fileName: String, data: String): Boolean {
        try {
            val file: File
            val rootDir = Environment.getExternalStorageDirectory()

            if (path != null) {
                file = File("$rootDir/$path", fileName)
                if (!file.exists()) {
                    file.mkdirs()
                }
            } else {
                file = File("$rootDir/$_defaultPath", fileName)
            }

            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
            return true
        } catch (ex: Exception) {
            Log.e("CustomFileSystemIO.write", ex.stackTraceToString())
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun read(context: Context, path: String?, fileName: String): String? {
        try {
            val file: File
            val rootDir = Environment.getExternalStorageDirectory()

            if(_activity != null) {
                ActivityHelper.checkAndAskReadWritePermissions(_activity)
            }

            file = if (path != null) {
                File("$rootDir/$path", fileName)
            } else {
                File("$rootDir/$_defaultPath", fileName)
            }

            if (file.exists()) {
                val fileInputStream = FileInputStream(file)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)

                val stringBuilder = StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                fileInputStream.close()
                return stringBuilder.toString()
            } else {
            Log.d("CustomFileSystemIO.read", "File not found: $fileName")
        }
        } catch (ex: Exception) {
            Log.e("CustomFileSystemIO.read", ex.stackTraceToString())
        }

        return null
    }

    override fun setActivity(activity: Activity) {
        _activity = activity
    }
}