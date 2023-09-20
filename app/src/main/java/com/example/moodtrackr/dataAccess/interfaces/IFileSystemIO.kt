package com.example.moodtrackr.dataAccess.interfaces

import android.app.Activity
import android.content.Context

interface IFileSystemIO {
    fun write(context: Context, path: String?, fileName: String, data: String): Boolean
    fun read(context: Context, path: String?, fileName: String): String?
    fun setActivity(activity: Activity)
}