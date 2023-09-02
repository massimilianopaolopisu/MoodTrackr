package com.example.moodtrackr.repositories

import android.content.Context
import android.content.SharedPreferences

open class SharedPreferencesRepository(val context: Context) {
    fun loadPreferences(key: String): SharedPreferences? {
        return try{
            context.getSharedPreferences(key, Context.MODE_PRIVATE)
        } catch(ex: Exception) {
            // TODO _logger.error(ex)
            null
        }
    }
}