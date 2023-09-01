package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.ThemePreferences

interface IThemePreferencesRepository {
    fun load() : ThemePreferences
    fun save(themePreferences : ThemePreferences)
}