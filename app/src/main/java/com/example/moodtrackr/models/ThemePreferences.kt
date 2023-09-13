package com.example.moodtrackr.models

import com.example.moodtrackr.enums.ThemeMode

class ThemePreferences(
    var themeMode: ThemeMode = ThemeMode.System,
    var dynamicColorsEnabled: Boolean = true,
    var lockOrientationEnabled: Boolean = false
) {
    val darkMode: Boolean
        get() {
                return when (themeMode) {
                ThemeMode.System -> {
                    false
                }

                ThemeMode.Light -> {
                    false
                }

                ThemeMode.Dark -> {
                    true
                }
            }
        }
}