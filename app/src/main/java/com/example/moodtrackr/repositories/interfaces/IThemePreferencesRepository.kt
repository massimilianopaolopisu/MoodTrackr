package com.example.moodtrackr.repositories.interfaces

import com.example.moodtrackr.models.ThemePreferences

interface IThemePreferencesRepository : ISave<ThemePreferences>, ILoad<ThemePreferences>