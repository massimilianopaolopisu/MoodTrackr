package com.example.moodtrackr.repositories.interfaces

import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

interface IMoodEntriesRepository : ISave<MoodEntry> {
    fun insertMoodEntry(t: MoodEntry): MoodEntry?
    fun getAllMoodEntries(): List<MoodEntry>
    fun getMoodEntriesInRange(startDate: LocalDate, endDate: LocalDate): List<MoodEntry>
    fun getMoodEntry(date: LocalDate): MoodEntry?
    fun deleteMoodEntry(date: LocalDate): Boolean
    fun updateMoodEntry(t: MoodEntry): MoodEntry?
}