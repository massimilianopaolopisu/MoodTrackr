package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate
import javax.inject.Inject

class MoodEntriesRepository @Inject constructor(context: Context) : GenericSqlRepository<MoodEntry>(context, MoodEntry()), IMoodEntriesRepository {

    override fun insertMoodEntry(t: MoodEntry): MoodEntry? {
        return insert(t)
    }

    override fun save(t: MoodEntry) {
        insert(t)
    }

    override fun getAllMoodEntries(): List<MoodEntry> {
        return getAll()
    }

    override fun getMoodEntriesInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry> {
        val selection = "${MoodEntry.COLUMN_DATE} BETWEEN ? AND ?"
        val selectionArgs = arrayOf(startDate.toString(), endDate.toString())

        return getItemsWithCustomSelection(selection, selectionArgs)
    }

    override fun getMoodEntry(date: LocalDate): MoodEntry? {
        return getByPrimaryKey(date)
    }

    override fun deleteMoodEntry(date: LocalDate): Boolean {
        return deleteByPrimaryKey(date)
    }

    override fun updateMoodEntry(t: MoodEntry): MoodEntry? {
        return update(t)
    }
}
