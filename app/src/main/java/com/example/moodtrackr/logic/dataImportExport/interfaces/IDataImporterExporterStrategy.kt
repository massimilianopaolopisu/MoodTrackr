package com.example.moodtrackr.logic.dataImportExport.interfaces

import android.app.Activity

interface IDataImporterExporterStrategy {
    fun export(outputFilePath: String?, fileName: String?): Boolean
    fun import(inputFilePath: String?, fileName: String?): Boolean
    fun setActivity(activity: Activity)
}