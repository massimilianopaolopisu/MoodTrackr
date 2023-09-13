package com.example.moodtrackr.models

import com.example.moodtrackr.enums.ThemeMode

class ThemePreferences(
    var themeMode: ThemeMode,
    var dynamicColorsEnabled: Boolean,
    var lockOrientationEnabled: Boolean
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