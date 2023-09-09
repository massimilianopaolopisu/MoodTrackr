package com.example.moodtrackr.logic.dataImportExport

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.moodtrackr.R
import com.example.moodtrackr.dataAccess.SharedPreferencesConnector
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class SharedPreferencesImporterExporterStrategy @Inject constructor(context: Context, private val fileSystemIO: IFileSystemIO) :
    SharedPreferencesConnector(context),
    IDataImporterExporterStrategy {

    private val _appName = context.getString(R.string.app_name)
    private val _fileName = "$_appName-sharedPreferences.json"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun export(outputFilePath: String?, fileName: String?): Boolean {
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
            fileSystemIO.write(context, fileName?: _fileName, jsonData)

            return true
        } catch (ex: Exception) {
            Log.e("SharedPreferencesImporterExporter.export", ex.stackTraceToString())
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun import(inputFilePath: String?, fileName: String?): Boolean {
        try {
            val gson = Gson()
            val jsonData = fileSystemIO.read(context, fileName?: _fileName)

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