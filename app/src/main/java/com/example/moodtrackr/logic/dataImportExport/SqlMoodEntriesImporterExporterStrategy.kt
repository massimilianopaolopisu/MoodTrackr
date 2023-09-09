package com.example.moodtrackr.logic.dataImportExport

import android.content.Context
import android.util.Log
import com.example.moodtrackr.R
import com.example.moodtrackr.adapters.LocalDateAdapter
import com.example.moodtrackr.dataAccess.GenericSqlWrapper
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.models.MoodEntry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import javax.inject.Inject

class SqlMoodEntriesImporterExporterStrategy @Inject constructor(private val context: Context, private val fileSystemIO: IFileSystemIO) :
    GenericSqlWrapper<MoodEntry>(context, MoodEntry()),
    IDataImporterExporterStrategy {

    private val _appName = context.getString(R.string.app_name)
    private val _fileName = "$_appName-sql-mood_entries-data.json"

    override fun export(outputFilePath: String?, fileName: String?): Boolean {
        return try {
            val moodEntries = getAll()

            val gson = Gson()
            val jsonData: String = gson.toJson(moodEntries)

            fileSystemIO.write(context, fileName?: _fileName, jsonData)
        } catch (ex: Exception) {
            Log.e("SqliteImporterExporter.export", ex.stackTraceToString())
            false
        }
    }

    override fun import(inputFilePath: String?, fileName: String?): Boolean {
        try {
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                .create()

            val jsonData = fileSystemIO.read(context, fileName?: _fileName)

            val moodEntriesType = object : TypeToken<List<MoodEntry>>() {}.type
            val moodEntries: List<MoodEntry> = gson.fromJson(jsonData, moodEntriesType)

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