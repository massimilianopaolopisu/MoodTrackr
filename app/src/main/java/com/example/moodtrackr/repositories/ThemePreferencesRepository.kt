package com.example.moodtrackr.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.SharedPreferencesKeys
import com.example.moodtrackr.models.ThemePreferences

class ThemePreferencesRepository(private val context: Context) : IThemePreferencesRepository
{
    override fun load() : ThemePreferences {
        val sharedPreferences = loadPreferences()
        val dynamicColorsEnabled = sharedPreferences.getBoolean("dynamicColorsEnabled", true)
        val themeModeString = sharedPreferences.getString("themeMode", "System") ?: "System"
        val themeMode = ThemeMode.valueOf(themeModeString)

        return ThemePreferences(themeMode, dynamicColorsEnabled)
    }

    override fun save(themePreferences: ThemePreferences) {
        val sharedPreferences = loadPreferences()
        val editor = sharedPreferences.edit()

        editor.putBoolean("dynamicColorsEnabled", themePreferences.dynamicColorsEnabled)
        editor.putString("themeMode", themePreferences.themeMode.toString())
        editor.apply()
    }

    private fun loadPreferences(): SharedPreferences {
        return context.getSharedPreferences(SharedPreferencesKeys.THEME, Context.MODE_PRIVATE)
    }
}