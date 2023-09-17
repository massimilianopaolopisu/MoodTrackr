package com.example.moodtrackr.helpers

import android.app.Activity
import android.content.pm.ActivityInfo

object ActivityHelper {
    fun setOrientation(activity: Activity, lockOrientationEnabled: Boolean) {
        activity.requestedOrientation = if(lockOrientationEnabled)
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }
}