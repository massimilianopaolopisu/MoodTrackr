package com.example.moodtrackr.dataAccess.interfaces

import android.content.Context

interface IFileSystemIO {
    fun write(context: Context, fileName: String, data: String): Boolean
    fun read(context: Context, fileName: String): String?
}