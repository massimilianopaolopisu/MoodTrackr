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

    override fun insertMoodEntry(moodEntry: MoodEntry): MoodEntry? {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase

            val values = ContentValues().apply {
                put(MoodEntryContract.MoodEntry.COLUMN_DATE, DateUtilities.getStringDateFromLocalDate(moodEntry.Date))
                put(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS, moodEntry.Happiness)
                put(MoodEntryContract.MoodEntry.COLUMN_ANGER, moodEntry.Anger)
                put(MoodEntryContract.MoodEntry.COLUMN_LOVE, moodEntry.Love)
                put(MoodEntryContract.MoodEntry.COLUMN_STRESS, moodEntry.Stress)
                put(MoodEntryContract.MoodEntry.COLUMN_ENERGY, moodEntry.Energy)
                put(MoodEntryContract.MoodEntry.COLUMN_SLEEP, moodEntry.Sleep)
                put(MoodEntryContract.MoodEntry.COLUMN_HEALTH, moodEntry.Health)
                put(MoodEntryContract.MoodEntry.COLUMN_DEPRESSION, moodEntry.Depression)
                put(MoodEntryContract.MoodEntry.COLUMN_NOTES, moodEntry.Notes)
            }

            val newRowId = db.insert(MoodEntryContract.MoodEntry.TABLE_NAME, null, values)

            if (newRowId != -1L) {
                val insertedRecord = getMoodEntry(moodEntry.Date)
                db.close()
                insertedRecord
            } else {
                db.close()
                null
            }
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
            null
        }
    }

    override fun getAllMoodEntries(): List<MoodEntry> {
        val moodEntryList = mutableListOf<MoodEntry>()

        try {
            val db = _sqlDatabaseHelper.readableDatabase

            val projection = arrayOf(
                MoodEntryContract.MoodEntry.COLUMN_DATE,
                MoodEntryContract.MoodEntry.COLUMN_HAPPINESS,
                MoodEntryContract.MoodEntry.COLUMN_ANGER,
                MoodEntryContract.MoodEntry.COLUMN_LOVE,
                MoodEntryContract.MoodEntry.COLUMN_STRESS,
                MoodEntryContract.MoodEntry.COLUMN_ENERGY,
                MoodEntryContract.MoodEntry.COLUMN_SLEEP,
                MoodEntryContract.MoodEntry.COLUMN_HEALTH,
                MoodEntryContract.MoodEntry.COLUMN_DEPRESSION,
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
                    val angerColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ANGER)
                    val loveColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                    val stressColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_STRESS)
                    val energyColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                    val sleepColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_SLEEP)
                    val healthColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                    val depressionColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_DEPRESSION)
                    val notesColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                    val date = DateUtilities.getLocalDateFromStringDate(getString(dateColumnIndex))
                    val happiness = getInt(happinessColumnIndex)
                    val anger = getInt(angerColumnIndex)
                    val love = getInt(loveColumnIndex)
                    val stress = getInt(stressColumnIndex)
                    val energy = getInt(energyColumnIndex)
                    val sleep = getInt(sleepColumnIndex)
                    val health = getInt(healthColumnIndex)
                    val depression = getInt(depressionColumnIndex)
                    val notes = getString(notesColumnIndex) ?: ""

                    val moodEntry = MoodEntry(date, happiness, anger, love, stress, energy, sleep, health, depression, notes)
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
                MoodEntryContract.MoodEntry.COLUMN_ANGER,
                MoodEntryContract.MoodEntry.COLUMN_LOVE,
                MoodEntryContract.MoodEntry.COLUMN_STRESS,
                MoodEntryContract.MoodEntry.COLUMN_ENERGY,
                MoodEntryContract.MoodEntry.COLUMN_SLEEP,
                MoodEntryContract.MoodEntry.COLUMN_HEALTH,
                MoodEntryContract.MoodEntry.COLUMN_DEPRESSION,
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
                    val angerColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ANGER)
                    val loveColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                    val stressColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_STRESS)
                    val energyColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                    val sleepColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_SLEEP)
                    val healthColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                    val depressionColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_DEPRESSION)
                    val notesColumnIndex = getColumnIndex(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                    val date = DateUtilities.getLocalDateFromStringDate(getString(dateColumnIndex))
                    val happiness = getInt(happinessColumnIndex)
                    val anger = getInt(angerColumnIndex)
                    val love = getInt(loveColumnIndex)
                    val stress = getInt(stressColumnIndex)
                    val energy = getInt(energyColumnIndex)
                    val sleep = getInt(sleepColumnIndex)
                    val health = getInt(healthColumnIndex)
                    val depression = getInt(depressionColumnIndex)
                    val notes = getString(notesColumnIndex) ?: ""

                    val moodEntry = MoodEntry(date, happiness, anger, love, stress, energy, sleep, health, depression, notes)
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
        return try {
            val db = _sqlDatabaseHelper.readableDatabase

            val projection = arrayOf(
                MoodEntryContract.MoodEntry.COLUMN_DATE,
                MoodEntryContract.MoodEntry.COLUMN_HAPPINESS,
                MoodEntryContract.MoodEntry.COLUMN_ANGER,
                MoodEntryContract.MoodEntry.COLUMN_LOVE,
                MoodEntryContract.MoodEntry.COLUMN_STRESS,
                MoodEntryContract.MoodEntry.COLUMN_ENERGY,
                MoodEntryContract.MoodEntry.COLUMN_SLEEP,
                MoodEntryContract.MoodEntry.COLUMN_HEALTH,
                MoodEntryContract.MoodEntry.COLUMN_DEPRESSION,
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
                    val happinessIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS)
                    val angerIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_ANGER)
                    val loveIndex = c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_LOVE)
                    val stressIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_STRESS)
                    val energyIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_ENERGY)
                    val sleepIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_SLEEP)
                    val healthIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_HEALTH)
                    val depressionIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_DEPRESSION)
                    val notesIndex =
                        c.getColumnIndexOrThrow(MoodEntryContract.MoodEntry.COLUMN_NOTES)

                    MoodEntry(
                        date = DateUtilities.getLocalDateFromStringDate(c.getString(dateIndex)),
                        happiness = c.getInt(happinessIndex),
                        anger = c.getInt(angerIndex),
                        love = c.getInt(loveIndex),
                        stress = c.getInt(stressIndex),
                        energy = c.getInt(energyIndex),
                        sleep = c.getInt(sleepIndex),
                        health = c.getInt(healthIndex),
                        depression = c.getInt(depressionIndex),
                        notes = c.getString(notesIndex)
                    )
                } else {
                    null
                }
            }
        } catch(ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
            null
        }
    }

    override fun deleteMoodEntry(date: LocalDate): Boolean {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase

            val selection = "${MoodEntryContract.MoodEntry.COLUMN_DATE} = ?"
            val selectionArgs = arrayOf(DateUtilities.getStringDateFromLocalDate(date))

            val rowsDeleted = db.delete(MoodEntryContract.MoodEntry.TABLE_NAME, selection, selectionArgs)
            db.close()

            rowsDeleted > 0
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
            false
        }
    }

    override fun updateMoodEntry(moodEntry: MoodEntry): MoodEntry? {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase

            val values = ContentValues().apply {
                put(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS, moodEntry.Happiness)
                put(MoodEntryContract.MoodEntry.COLUMN_HAPPINESS, moodEntry.Happiness)
                put(MoodEntryContract.MoodEntry.COLUMN_ANGER, moodEntry.Anger)
                put(MoodEntryContract.MoodEntry.COLUMN_LOVE, moodEntry.Love)
                put(MoodEntryContract.MoodEntry.COLUMN_STRESS, moodEntry.Stress)
                put(MoodEntryContract.MoodEntry.COLUMN_ENERGY, moodEntry.Energy)
                put(MoodEntryContract.MoodEntry.COLUMN_SLEEP, moodEntry.Sleep)
                put(MoodEntryContract.MoodEntry.COLUMN_HEALTH, moodEntry.Health)
                put(MoodEntryContract.MoodEntry.COLUMN_DEPRESSION, moodEntry.Depression)
                put(MoodEntryContract.MoodEntry.COLUMN_NOTES, moodEntry.Notes)            }

            val selection = "${MoodEntryContract.MoodEntry.COLUMN_DATE} = ?"
            val selectionArgs = arrayOf(DateUtilities.getStringDateFromLocalDate(moodEntry.Date))

            val updatedRows = db.update(
                MoodEntryContract.MoodEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )

            if (updatedRows > 0) {
                val updatedRecord = getMoodEntry(moodEntry.Date)
                db.close()
                updatedRecord
            } else {
                db.close()
                null
            }
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
            null
        }
    }
}