package com.example.moodtrackr.helpers

import android.app.Activity
import android.util.Log
import android.widget.Toast

object ToastNotificationHelper {
    fun showShortToastNotification(activity: Activity?, message: String) {
        if(activity == null)
            return

        try {
            val toast = Toast.makeText(activity , message, Toast.LENGTH_SHORT)
            toast.show()
        } catch(ex: Exception) {
            Log.e("ToastNotificationHelper", ex.stackTraceToString())
        }
    }
}