package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

interface IMoodEntriesRepository {
    fun insertMoodEntry(moodEntry: MoodEntry)
    fun getAllMoodEntries(): List<MoodEntry>
    fun getMoodEntriesInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>
    fun getMoodEntry(date: LocalDate): MoodEntry?
}