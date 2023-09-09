package com.example.moodtrackr.logic.dataImportExport.interfaces

interface IDataImporterExporterStrategy {
    fun export(outputFilePath: String?, fileName: String?): Boolean
    fun import(inputFilePath: String?, fileName: String?): Boolean
}