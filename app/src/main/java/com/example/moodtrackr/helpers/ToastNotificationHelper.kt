package com.example.moodtrackr.helpers

import android.app.Activity
import android.widget.Toast

object ToastNotificationHelper {
    fun showShortToastNotification(activity: Activity, message: String) {
        val toast = Toast.makeText(activity , message, Toast.LENGTH_SHORT)
        toast.show()
    }
}