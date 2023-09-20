package com.example.moodtrackr.logic.dataImportExport

import android.app.Activity
import android.util.Log
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import javax.inject.Inject

class DataImporterExporterStrategy @Inject constructor(
    private var strategies: List<IDataImporterExporterStrategy>
) : IDataImporterExporterStrategy {

    private var _activity: Activity? = null

    override fun export(outputFilePath: String?, fileName: String?): Boolean {
        var success = true

        if (!strategies.any())
            return true

        strategies.forEach {
            try {
                if(_activity != null) {
                    it.setActivity(_activity!!)
                }

                val result = it.export(outputFilePath, fileName)

                if (!result)
                    success = false
            } catch (ex: Exception) {
                Log.e("DataImporterExportStrategy.export", ex.stackTraceToString())
                success = false
            }
        }

        return success
    }

    override fun import(inputFilePath: String?, fileName: String?): Boolean {
        var success = true

        if (!strategies.any())
            return true

        strategies.forEach {
            try {
                if(_activity != null) {
                    it.setActivity(_activity!!)
                }

                val result = it.import(inputFilePath, fileName)

                if (!result)
                    success = false
            } catch (ex: Exception) {
                Log.e("DataImporterExportStrategy.export", ex.stackTraceToString())
                success = false
            }
        }

        return success
    }

    override fun setActivity(activity: Activity) {
        _activity = activity
    }
}