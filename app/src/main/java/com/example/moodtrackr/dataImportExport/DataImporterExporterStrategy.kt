package com.example.moodtrackr.dataImportExport

import android.util.Log
import com.example.moodtrackr.dataImportExport.interfaces.IDataImporterExporterStrategy
import javax.inject.Inject

class DataImporterExporterStrategy @Inject constructor(private var strategies: List<IDataImporterExporterStrategy>) : IDataImporterExporterStrategy {
    override fun export(outputFilePath: String?): Boolean {
        var success = true

        if (!strategies.any())
            return true

        strategies.forEach {
            try {
                val result = it.export(null)
                if (!result)
                    success = false
            } catch (ex: Exception) {
                Log.e("DataImporterExportStrategy.export", ex.stackTraceToString())
                success = false
            }
        }

        return success
    }

    override fun import(inputFilePath: String?): Boolean {
        var success = true

        if (!strategies.any())
            return true

        strategies.forEach {
            try {
                val result = it.import(null)
                if (!result)
                    success = false
            } catch (ex: Exception) {
                Log.e("DataImporterExportStrategy.export", ex.stackTraceToString())
                success = false
            }
        }

        return success
    }
}