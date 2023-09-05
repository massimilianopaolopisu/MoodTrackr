package com.example.moodtrackr.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.ApplicationPreferencesRepository

class SqlDatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_MOOD_ENTRIES_TABLE)

        val applicationPreferencesRepository = ApplicationPreferencesRepository(context)
        val applicationPreferences = applicationPreferencesRepository.load()
        applicationPreferences.sqlDatabaseExists = true
        applicationPreferences.sqlMoodEntryTableExists = true
        applicationPreferencesRepository.save(applicationPreferences)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        when (oldVersion) {
            1 -> {
            }
        }
    }

    companion object {
        const val DATABASE_NAME = "mood_trackr.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_MOOD_ENTRIES_TABLE =
            "CREATE TABLE ${MoodEntry.TABLE_NAME} (" +
                    "${MoodEntry.COLUMN_DATE} TEXT PRIMARY KEY," +
                    "${MoodEntry.COLUMN_HAPPINESS} INTEGER," +
                    "${MoodEntry.COLUMN_ANGER} INTEGER," +
                    "${MoodEntry.COLUMN_LOVE} INTEGER," +
                    "${MoodEntry.COLUMN_STRESS} INTEGER," +
                    "${MoodEntry.COLUMN_ENERGY} INTEGER," +
                    "${MoodEntry.COLUMN_SLEEP} INTEGER," +
                    "${MoodEntry.COLUMN_HEALTH} INTEGER," +
                    "${MoodEntry.COLUMN_DEPRESSION} INTEGER," +
                    "${MoodEntry.COLUMN_NOTES} TEXT)"
    }
}
