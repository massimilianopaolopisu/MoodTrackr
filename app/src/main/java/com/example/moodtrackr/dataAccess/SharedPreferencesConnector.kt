package com.example.moodtrackr.dataAccess

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.moodtrackr.models.SharedPreferencesKeys

open class SharedPreferencesConnector(val context: Context) {
    fun loadPreferences(key: String): SharedPreferences? {
        return try{
            context.getSharedPreferences(key, Context.MODE_PRIVATE)
        } catch(ex: Exception) {
            Log.e("SharedPreferencesRepository.loadPreferences", ex.stackTraceToString())
            null
        }
    }

    fun getAllSharedPreferencesKeys(): List<String> {
        return SharedPreferencesKeys.getAllKeys()
    }
}