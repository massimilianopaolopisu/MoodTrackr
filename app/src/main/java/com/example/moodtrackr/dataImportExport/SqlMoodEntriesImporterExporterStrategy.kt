package com.example.moodtrackr.dataImportExport

import android.content.Context
import android.util.Log
import com.example.moodtrackr.adapters.LocalDateAdapter
import com.example.moodtrackr.R
import com.example.moodtrackr.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.dataAccess.GenericSqlWrapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.FileReader
import java.time.LocalDate
import javax.inject.Inject

class SqlMoodEntriesImporterExporterStrategy @Inject constructor(context: Context) : GenericSqlWrapper<MoodEntry>(context, MoodEntry()), IDataImporterExporterStrategy {

    private val _dataDirectory = "backup"
    private val _appName = context.getString(R.string.app_name)
    private val _defaultFilePath = File(context.getExternalFilesDir(_dataDirectory), "$_appName-sql-mood_entries-data.json").absolutePath

    override fun export(outputFilePath: String?): Boolean {
        try {
            val moodEntries = getAll()

            val gson = Gson()
            val jsonData: String = gson.toJson(moodEntries)

            val file = File(outputFilePath ?: _defaultFilePath)
            val fileWriter = FileWriter(file)
            fileWriter.write(jsonData)
            fileWriter.close()

            return true
        } catch (ex: Exception) {
            Log.e("SqliteImporterExporter.export", ex.stackTraceToString())
            return false
        }
    }

    override fun import(inputFilePath: String?): Boolean {
        try {
            val filePath = inputFilePath ?: _defaultFilePath
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                .create()

            val file = File(filePath)

            if (!file.exists()) {
                Log.e("SqliteImporterExporter.import", "File $filePath doesn't exist")
                return false
            }

            val moodEntriesType = object : TypeToken<List<MoodEntry>>() {}.type
            val moodEntries: List<MoodEntry> = gson.fromJson(FileReader(file), moodEntriesType)

            for (entry in moodEntries) {
                insert(entry)
            }

            return true
        } catch (ex: Exception) {
            Log.e("SqliteImporterExporter.import", ex.stackTraceToString())
            return false
        }
    }
}