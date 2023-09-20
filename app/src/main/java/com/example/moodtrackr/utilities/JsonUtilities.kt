package com.example.moodtrackr.utilities

import android.util.Log

object JsonUtilities {
    fun fixJsonEnd(json: String?, closureBracketsCount: Int): String? {
        if (json.isNullOrBlank())
            return json

        try {
            var end = ""

            for(i in 0 until closureBracketsCount) {
                end += "}"
            }

            val cleanedData = json.replace("\\}\\}\\}$".toRegex()) {
                end
            }

            return cleanedData
        } catch (ex: Exception) {
            Log.e("JsonUtilities.fixJsonEnd", ex.stackTraceToString())
            return json
        }
    }
}