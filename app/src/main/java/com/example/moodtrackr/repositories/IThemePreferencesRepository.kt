package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.ThemePreferences

interface IThemePreferencesRepository: ISave<ThemePreferences> {
    fun load() : ThemePreferences
}