package com.example.moodtrackr.repositories

import android.content.Context
import android.util.Log
import com.example.moodtrackr.dataAccess.SharedPreferencesConnector
import com.example.moodtrackr.models.ApplicationPreferences
import com.example.moodtrackr.models.SharedPreferencesKeys
import javax.inject.Inject

class ApplicationPreferencesRepository @Inject constructor(context: Context) : SharedPreferencesConnector(context),
    IApplicationPreferencesRepository {
    override fun load(): ApplicationPreferences {
        val applicationPreferences = ApplicationPreferences()

        try {
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.APPLICATION) ?: return applicationPreferences

            applicationPreferences.sqlDatabaseExists = sharedPreferences.getBoolean("sqlDatabaseExists", false)
            applicationPreferences.sqlMoodEntryTableExists = sharedPreferences.getBoolean("sqlMoodEntryTableExists", false)

            return applicationPreferences
        } catch(ex: Exception) {
            Log.e("ProfilePreferencesRepository", ex.stackTraceToString())
            return applicationPreferences
        }
    }

    override fun save(t: ApplicationPreferences) {
        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.APPLICATION) ?: return
            val editor = sharedPreferences.edit()

            editor.putBoolean("sqlDatabaseExists", t.sqlDatabaseExists)
            editor.putBoolean("sqlMoodEntryTableExists", t.sqlMoodEntryTableExists)
            editor.apply()
        } catch (ex: Exception) {
            Log.e("ApplicationPreferencesRepository", ex.stackTraceToString())
        }
    }
}