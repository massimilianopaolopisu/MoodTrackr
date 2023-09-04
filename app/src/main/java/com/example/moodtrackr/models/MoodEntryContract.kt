package com.example.moodtrackr.models

import android.provider.BaseColumns

object MoodEntryContract {
    object MoodEntry : BaseColumns {
        const val TABLE_NAME = "mood_entries"
        const val COLUMN_DATE = "date"
        const val COLUMN_HAPPINESS = "happiness"
        const val COLUMN_ANGER = "anger"
        const val COLUMN_LOVE = "love"
        const val COLUMN_STRESS = "stress"
        const val COLUMN_ENERGY = "energy"
        const val COLUMN_SLEEP = "sleep"
        const val COLUMN_HEALTH = "health"
        const val COLUMN_DEPRESSION = "depression"
        const val COLUMN_NOTES = "notes"
    }
}
