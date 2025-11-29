package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.dataAccess.GenericSqlWrapper
import com.example.moodtrackr.models.MoodEntryCustomMood
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import java.time.LocalDate
import javax.inject.Inject

class MoodEntryCustomMoodsRepository @Inject constructor(private val context: Context) : ISave<List<MoodEntryCustomMood>> {

    private val wrapper = GenericSqlWrapper<MoodEntryCustomMood>(context, MoodEntryCustomMood())

    fun getByDate(date: LocalDate): List<MoodEntryCustomMood> {
        return wrapper.getItemsWithCustomSelection("${MoodEntryCustomMood.COLUMN_ENTRY_DATE} = ?", arrayOf(date.toString()))
    }

    fun deleteByDate(date: LocalDate) {
        val db = SqlDatabaseHelper(context).writableDatabase
        db.delete(MoodEntryCustomMood.TABLE_NAME, "${MoodEntryCustomMood.COLUMN_ENTRY_DATE} = ?", arrayOf(date.toString()))
        db.close()
    }

    override fun save(t: List<MoodEntryCustomMood>) {
        if (t.isEmpty()) return

        val date = t[0].entryDate
        deleteByDate(date)

        for (item in t) {
            wrapper.insert(item)
        }
    }
}
