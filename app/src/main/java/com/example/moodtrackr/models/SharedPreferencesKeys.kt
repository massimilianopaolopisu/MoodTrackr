package com.example.moodtrackr.models

object SharedPreferencesKeys {
    const val PROFILE = "Profile"
    const val THEME = "Theme"
    const val APPLICATION = "Application"

    fun getAllKeys(): List<String> {
        return listOf(PROFILE,THEME,APPLICATION)
    }
}