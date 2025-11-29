package com.example.moodtrackr.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moodtrackr.models.CustomMood
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.models.MoodEntryCustomMood
import com.example.moodtrackr.repositories.ApplicationPreferencesRepository

class SqlDatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_MOOD_ENTRIES_TABLE)
        db.execSQL(SQL_CREATE_CUSTOM_MOODS_TABLE)
        db.execSQL(SQL_CREATE_MOOD_ENTRY_CUSTOM_MOODS_TABLE)

        val applicationPreferencesRepository = ApplicationPreferencesRepository(context)
        val applicationPreferences = applicationPreferencesRepository.load()
        applicationPreferences.sqlDatabaseExists = true
        applicationPreferences.sqlMoodEntryTableExists = true
        applicationPreferencesRepository.save(applicationPreferences)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)

        
        try {
            fun tableExists(tableName: String): Boolean {
                val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", arrayOf(tableName))
                val exists = cursor.count > 0
                cursor.close()
                return exists
            }

            if (!tableExists(MoodEntry.TABLE_NAME)) {
                db.execSQL(SQL_CREATE_MOOD_ENTRIES_TABLE)
            }

            if (!tableExists(CustomMood.TABLE_NAME)) {
                db.execSQL(SQL_CREATE_CUSTOM_MOODS_TABLE)
            }

            if (!tableExists(MoodEntryCustomMood.TABLE_NAME)) {
                db.execSQL(SQL_CREATE_MOOD_ENTRY_CUSTOM_MOODS_TABLE)
            }
        } catch (ex: Exception) {
        }
    }

    companion object {
        const val DATABASE_NAME = "mood_trackr.db"
        const val DATABASE_VERSION = 4
        private const val GRAY_COLOR_VALUE = 0xFFA9A9A9L

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

        private const val SQL_CREATE_CUSTOM_MOODS_TABLE =
            "CREATE TABLE ${CustomMood.TABLE_NAME} (" +
                "${CustomMood.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CustomMood.COLUMN_NAME} TEXT NOT NULL," +
                "${CustomMood.COLUMN_COLOR} INTEGER DEFAULT $GRAY_COLOR_VALUE)"

        private const val SQL_CREATE_MOOD_ENTRY_CUSTOM_MOODS_TABLE =
            "CREATE TABLE mood_entry_custom_moods (" +
                "entry_date TEXT NOT NULL," +
                "custom_mood_id INTEGER NOT NULL," +
                "value INTEGER DEFAULT 50," +
                "PRIMARY KEY (entry_date, custom_mood_id)," +
                "FOREIGN KEY (entry_date) REFERENCES ${MoodEntry.TABLE_NAME}(${MoodEntry.COLUMN_DATE})," +
                "FOREIGN KEY (custom_mood_id) REFERENCES ${CustomMood.TABLE_NAME}(${CustomMood.COLUMN_ID}))"
    }
}
