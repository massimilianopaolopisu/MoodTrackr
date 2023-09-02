package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.MoodEntry

interface IMoodEntriesRepository : ISave<MoodEntry>, ILoad<MoodEntry?> {
}