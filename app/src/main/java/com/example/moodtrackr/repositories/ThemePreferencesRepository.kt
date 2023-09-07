package com.example.moodtrackr.repositories

import android.content.Context
import android.util.Log
import com.example.moodtrackr.dataAccess.SharedPreferencesConnector
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.SharedPreferencesKeys
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
import javax.inject.Inject

class ThemePreferencesRepository @Inject constructor(context: Context) : SharedPreferencesConnector(context),
    IThemePreferencesRepository
{
    override fun load() : ThemePreferences {
        val themePreferences = ThemePreferences(
            ThemeMode.System,
            dynamicColorsEnabled = true,
            lockOrientationEnabled = false
        )

        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.THEME) ?: return themePreferences

            val dynamicColorsEnabled = sharedPreferences.getBoolean("dynamicColorsEnabled", true)
            val lockOrientationEnabled = sharedPreferences.getBoolean("lockOrientationEnabled", false)
            val themeModeString = sharedPreferences.getString("themeMode", "System") ?: "System"
            val themeMode = ThemeMode.valueOf(themeModeString)

            return ThemePreferences(themeMode, dynamicColorsEnabled, lockOrientationEnabled)
        } catch(ex: Exception)
        {
            Log.e("ThemePreferencesRepository.load", ex.stackTraceToString())
            return themePreferences
        }
    }

    override fun save(t: ThemePreferences) {
        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.THEME) ?: return
            val editor = sharedPreferences.edit()

            editor.putBoolean("dynamicColorsEnabled", t.dynamicColorsEnabled)
            editor.putBoolean("lockOrientationEnabled", t.lockOrientationEnabled)
            editor.putString("themeMode", t.themeMode.toString())
            editor.apply()
        } catch(ex: Exception) {
            Log.e("ThemePreferencesRepository.save", ex.stackTraceToString())
        }
    }
}