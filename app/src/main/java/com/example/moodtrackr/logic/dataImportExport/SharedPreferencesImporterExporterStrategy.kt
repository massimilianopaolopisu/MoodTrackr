package com.example.moodtrackr.logic.dataImportExport

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.moodtrackr.R
import com.example.moodtrackr.dataAccess.SharedPreferencesConnector
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import java.io.File
import java.io.FileWriter
import javax.inject.Inject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesImporterExporterStrategy @Inject constructor(context: Context) : SharedPreferencesConnector(context),
    IDataImporterExporterStrategy {

    private val _dataDirectory = "backup"
    private val _appName = context.getString(R.string.app_name)
    private val _defaultFilePath = File(context.getExternalFilesDir(_dataDirectory), "$_appName-sharedPreferences.json").absolutePath

    override fun export(outputFilePath: String?): Boolean {
        try {
            val keys = getAllSharedPreferencesKeys()

            val gson = Gson()
            val allData = mutableMapOf<String, Any>()

            for (key in keys) {
                val sharedPreferences = loadPreferences(key)
                val data = sharedPreferences?.all
                data?.let {
                    allData[key] = it
                }
            }

            val jsonData: String = gson.toJson(allData)

            val file = File(outputFilePath?: _defaultFilePath)
            val fileWriter = FileWriter(file)
            fileWriter.write(jsonData)
            fileWriter.close()

            return true
        } catch (ex: Exception) {
            Log.e("SharedPreferencesImporterExporter.export", ex.stackTraceToString())
            return false
        }
    }

    override fun import(inputFilePath: String?): Boolean {
        try {
            val filePath = inputFilePath?: _defaultFilePath
            val gson = Gson()
            val file = File(filePath)

            if (!file.exists()) {
                Log.e("SharedPreferencesImporterExporter.import", "File $filePath doesn't exists ")
                return false
            }

            val jsonData: String = file.readText()

            val type = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
            val deserializedData: Map<String, Map<String, Any>> = gson.fromJson(jsonData, type)

            for ((key, valueMap) in deserializedData) {
                val sharedPreferences = loadPreferences(key)

                sharedPreferences?.let {
                    val editor: SharedPreferences.Editor = it.edit()
                    for ((subKey, subValue) in valueMap) {
                        when (subValue) {
                            is Boolean -> editor.putBoolean(subKey, subValue)
                            is Int -> editor.putInt(subKey, subValue)
                            is Long -> editor.putLong(subKey, subValue)
                            is Float -> editor.putFloat(subKey, subValue)
                            is String -> editor.putString(subKey, subValue)
                        }
                    }
                    editor.apply()
                }
            }

            return true
        } catch (ex: Exception) {
            Log.e("SharedPreferencesImporterExporter.import", ex.stackTraceToString())
            return false
        }
    }
}