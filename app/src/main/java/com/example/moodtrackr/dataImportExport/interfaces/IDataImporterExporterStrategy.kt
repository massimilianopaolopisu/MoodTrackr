package com.example.moodtrackr.dataImportExport.interfaces

interface IDataImporterExporterStrategy {
    fun export(outputFilePath: String?): Boolean
    fun import(inputFilePath: String?): Boolean
}