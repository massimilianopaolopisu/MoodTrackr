package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.models.SharedPreferencesKeys
import java.time.LocalDate

class MoodEntriesRepository(context: Context) : SharedPreferencesRepository(context), IMoodEntriesRepository {
    override fun save(moodEntry: MoodEntry) {
        TODO("Not yet implemented")
    }

    override fun load(): MoodEntry? {
        TODO("Not yet implemented")
    }

    fun loadAll(): List<MoodEntry>{
        TODO("Not yet implemented")
    }

    fun loadInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>{
        TODO("Not yet implemented")
    }

    private fun loadPreferences(date: LocalDate): MoodEntry? {
        val key = String.format(SharedPreferencesKeys.PROFILE, date.toString())
        val moodEntryPreferences = loadPreferences(key) ?: return null

        val happiness = moodEntryPreferences.getInt("happiness", 0)
        val love = moodEntryPreferences.getInt("love", 0)
        val energy = moodEntryPreferences.getInt("energy", 0)
        val health = moodEntryPreferences.getInt("health", 0)
        val notes = moodEntryPreferences.getString("notes", "") ?: ""

        return MoodEntry(date, happiness, love, energy, health, notes)
    }
}