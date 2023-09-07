package com.example.moodtrackr.logic.dataImportExport.interfaces

interface IDataImporterExporterStrategy {
    fun export(outputFilePath: String?): Boolean
    fun import(inputFilePath: String?): Boolean
}