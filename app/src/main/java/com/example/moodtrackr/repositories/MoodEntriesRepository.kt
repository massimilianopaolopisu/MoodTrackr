package com.example.moodtrackr.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.models.MoodEntryContract
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate
import javax.inject.Inject

class MoodEntriesRepository @Inject constructor(context: Context) : IMoodEntriesRepository {
    private val _sqlDatabaseHelper = SqlDatabaseHelper(context)

    override fun insertMoodEntry(moodEntry: MoodEntry) {
        try{
            val db = _sqlDatabaseHelper.writableDatabase

            val values = ContentValues().apply {
                put(MoodEntryContract.MoodEntry.COLUMN_DATE, DateUtilities.getStringDateFromLocalDate(moodEntry.date))
                put(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS, moodEntry.happiness)
                put(MoodEntryContract.MoodEntry.COLUMN_LOVE, moodEntry.love)
                put(MoodEntryContract.MoodEntry.COLUMN_ENERGY, moodEntry.energy)
                put(MoodEntryContract.MoodEntry.COLUMN_HEALTH, moodEntry.health)
                put(MoodEntryContract.MoodEntry.COLUMN_NOTES, moodEntry.notes)
            }

            db.insert(MoodEntryContract.MoodEntry.TABLE_NAME, null, values)
            db.close()
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
        }
    }

    override fun getAllMoodEntries(): List<MoodEntry> {
        val moodEntryList = mutableListOf<MoodEntry>()

        try {
            val db = _sqlDatabaseHelper.readableDatabase

            val projection = arrayOf(
                MoodEntryContract.MoodEntry.COLUMN_DATE,
                MoodEntryContract.MoodEntry.COLUMN_HAPPINESS,
                MoodEntryContract.MoodEntry.COLUMN_LOVE,
                MoodEntryContract.MoodEntry.COLUMN_ENERGY,
                MoodEntryContract.MoodEntry.COLUMN_HEALTH,
                MoodEntryContract.MoodEntry.COLUMN_NOTES
            )

            val cursor: Cursor = db.query(
                MoodEntryContract.MoodEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            with(cursor) {
                while (moveToNext()) {
                    val dateColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_DATE)
                    val happinessColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS)
                    val loveColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                    val energyColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                    val healthColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                    val notesColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                    val date = DateUtilities.getLocalDateFromStringDate(getString(dateColumnIndex))
                    val happiness = getInt(happinessColumnIndex)
                    val love = getInt(loveColumnIndex)
                    val energy = getInt(energyColumnIndex)
                    val health = getInt(healthColumnIndex)
                    val notes = getString(notesColumnIndex) ?: ""

                    val moodEntry = MoodEntry(date, happiness, love, energy, health, notes)
                    moodEntryList.add(moodEntry)
                }
            }

            cursor.close()
            db.close()
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
        }

        return moodEntryList
    }

    override fun getMoodEntriesInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>{
        val moodEntryInRangeList = mutableListOf<MoodEntry>()

        try {
            val db = _sqlDatabaseHelper.readableDatabase

            val projection = arrayOf(
                MoodEntryContract.MoodEntry.COLUMN_DATE,
                MoodEntryContract.MoodEntry.COLUMN_HAPPINESS,
                MoodEntryContract.MoodEntry.COLUMN_LOVE,
                MoodEntryContract.MoodEntry.COLUMN_ENERGY,
                MoodEntryContract.MoodEntry.COLUMN_HEALTH,
                MoodEntryContract.MoodEntry.COLUMN_NOTES
            )

            val selection =
                "${MoodEntryContract.MoodEntry.COLUMN_DATE} BETWEEN ? AND ?"
            val selectionArgs = arrayOf(
                DateUtilities.getStringDateFromLocalDate(startDate),
                DateUtilities.getStringDateFromLocalDate(endDate)
            )

            val cursor: Cursor = db.query(
                MoodEntryContract.MoodEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            with(cursor) {
                while (moveToNext()) {
                    val dateColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_DATE)
                    val happinessColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS)
                    val loveColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                    val energyColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                    val healthColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                    val notesColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                    val date = DateUtilities.getLocalDateFromStringDate(getString(dateColumnIndex))
                    val happiness = getInt(happinessColumnIndex)
                    val love = getInt(loveColumnIndex)
                    val energy = getInt(energyColumnIndex)
                    val health = getInt(healthColumnIndex)
                    val notes = getString(notesColumnIndex) ?: ""

                    val moodEntry = MoodEntry(date, happiness, love, energy, health, notes)
                    moodEntryInRangeList.add(moodEntry)
                }
            }

            cursor.close()
            db.close()
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
        }

        return moodEntryInRangeList
    }

    override fun getMoodEntry(date: LocalDate): MoodEntry? {
        val db = _sqlDatabaseHelper.readableDatabase

        val projection = arrayOf(
            MoodEntryContract.MoodEntry.COLUMN_DATE,
            MoodEntryContract.MoodEntry.COLUMN_HAPPINESS,
            MoodEntryContract.MoodEntry.COLUMN_LOVE,
            MoodEntryContract.MoodEntry.COLUMN_ENERGY,
            MoodEntryContract.MoodEntry.COLUMN_HEALTH,
            MoodEntryContract.MoodEntry.COLUMN_NOTES
        )

        val selection = "${MoodEntryContract.MoodEntry.COLUMN_DATE} = ?"
        val selectionArgs = arrayOf(DateUtilities.getStringDateFromLocalDate(date))

        val cursor = db.query(
            MoodEntryContract.MoodEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        return cursor.use { c ->
            if (c.moveToFirst()) {
                val dateIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_DATE)
                val happinessIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS)
                val loveIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                val energyIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                val healthIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                val notesIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                MoodEntry(
                    date = DateUtilities.getLocalDateFromStringDate(c.getString(dateIndex)),
                    happiness = c.getInt(happinessIndex),
                    love = c.getInt(loveIndex),
                    energy = c.getInt(energyIndex),
                    health = c.getInt(healthIndex),
                    notes = c.getString(notesIndex)
                )
            } else {
                null
            }
        }
    }
}