package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.SharedPreferencesKeys
import com.example.moodtrackr.models.ThemePreferences

class ThemePreferencesRepository(context: Context) : SharedPreferencesRepository(context), IThemePreferencesRepository
{
    override fun load() : ThemePreferences {
        val themePreferences = ThemePreferences(ThemeMode.System, true)

        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.THEME) ?: return themePreferences

            val dynamicColorsEnabled = sharedPreferences.getBoolean("dynamicColorsEnabled", true)
            val themeModeString = sharedPreferences.getString("themeMode", "System") ?: "System"
            val themeMode = ThemeMode.valueOf(themeModeString)

            return ThemePreferences(themeMode, dynamicColorsEnabled)
        } catch(ex: Exception)
        {
            // TODO _logger.error(ex)
            return themePreferences
        }
    }

    override fun save(themePreferences: ThemePreferences) {
        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.THEME) ?: return
            val editor = sharedPreferences.edit()

            editor.putBoolean("dynamicColorsEnabled", themePreferences.dynamicColorsEnabled)
            editor.putString("themeMode", themePreferences.themeMode.toString())
            editor.apply()
        } catch(ex: Exception) {
            // TODO _logger.error(ex)
        }
    }
}