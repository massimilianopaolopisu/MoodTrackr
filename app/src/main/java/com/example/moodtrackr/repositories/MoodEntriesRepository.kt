package com.example.moodtrackr.repositories

import android.content.Context
import android.util.Log
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.models.SharedPreferencesKeys
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

class MoodEntriesRepository(context: Context) : SharedPreferencesRepository(context), IMoodEntriesRepository {
    override fun save(t: MoodEntry) {
        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.MOOD_ENTRY) ?: return
            val editor = sharedPreferences.edit()

            editor.putString("date", DateUtilities.getStringDateFromLocalDate(t.date))
            editor.putInt("happiness", t.happiness)
            editor.putInt("love", t.love)
            editor.putInt("energy", t.energy)
            editor.putInt("health", t.energy)
            editor.apply()
        } catch (ex: Exception) {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
        }    }

    override fun load(): List<MoodEntry> {
        TODO("Not yet implemented")
    }

    fun loadInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>{
        TODO("Not yet implemented")
    }

    private fun loadMoodEntry(date: LocalDate): MoodEntry? {
        val key = String.format(SharedPreferencesKeys.MOOD_ENTRY, date.toString())

        try{
            val moodEntryPreferences = loadPreferences(key) ?: return null

            val happiness = moodEntryPreferences.getInt("happiness", 0)
            val love = moodEntryPreferences.getInt("love", 0)
            val energy = moodEntryPreferences.getInt("energy", 0)
            val health = moodEntryPreferences.getInt("health", 0)
            val notes = moodEntryPreferences.getString("notes", "") ?: ""

            return MoodEntry(date, happiness, love, energy, health, notes)
        } catch (ex: Exception)
        {
            Log.e("MoodEntriesRepository", ex.stackTraceToString())
            return null
        }
    }
}