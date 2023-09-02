package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

class MoodEntriesRepository: IMoodEntriesRepository {
    override fun save(t: MoodEntry) {
        TODO("Not yet implemented")
    }

    override fun load(): MoodEntry {
        TODO("Not yet implemented")
    }

    fun loadAll(): List<MoodEntry>{
        TODO("Not yet implemented")
    }

    fun loadInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>{
        TODO("Not yet implemented")
    }
}