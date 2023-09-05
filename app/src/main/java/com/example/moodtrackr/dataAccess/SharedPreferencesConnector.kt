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

    fun loadAllPreferences(): List<SharedPreferences>{
        val sharedPreferencesList = mutableListOf<SharedPreferences>()

        val keys = getAllSharedPreferencesKeys()

        if (!keys.any())
            return sharedPreferencesList

        keys.forEach {
            val sharedPreferences = loadPreferences(it)

            if (sharedPreferences != null)
                sharedPreferencesList.add(sharedPreferences)
        }

        return sharedPreferencesList
    }

    fun getAllSharedPreferencesKeys(): List<String> {
        return SharedPreferencesKeys.getAllKeys()
    }
}