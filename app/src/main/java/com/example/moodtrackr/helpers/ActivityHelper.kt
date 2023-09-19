package com.example.moodtrackr.helpers

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log

object ActivityHelper {
    fun setOrientation(activity: Activity?, lockOrientationEnabled: Boolean) {

        if(activity == null)
            return

        try {
            activity.requestedOrientation = if(lockOrientationEnabled)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        } catch(ex: Exception) {
            Log.e("ActivityHelper.setOrientation", ex.stackTraceToString())
        }
    }
}