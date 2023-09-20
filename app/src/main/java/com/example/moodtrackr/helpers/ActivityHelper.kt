package com.example.moodtrackr.helpers

import android.R.color
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity

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

    fun resetWindowBackground(activity: Activity?) {
        if(activity == null)
            return

        try {
            activity.window?.decorView?.post {
                activity.window?.setBackgroundDrawableResource(color.transparent)
            }
        } catch(ex: Exception) {
            Log.e("ActivityHelper.resetWindowBackground", ex.stackTraceToString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun checkReadWritePermissions(activity: Activity?): Boolean {
        var permissionsGranted = false

        if(activity == null)
            return permissionsGranted

        try {
            permissionsGranted = Environment.isExternalStorageManager()
        } catch(ex: Exception) {
            Log.e("ActivityHelper.checkReadWritePermissions", ex.stackTraceToString())
        }

        return permissionsGranted
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun askReadWritePermissions(activity: Activity?) {
        if(activity == null)
            return

        try {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(activity, intent, null)
        } catch(ex: Exception) {
            Log.e("ActivityHelper.checkReadWritePermissions", ex.stackTraceToString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun checkAndAskReadWritePermissions(activity: Activity?) {
        val permissionsGranted = checkReadWritePermissions(activity)

        if(!permissionsGranted)
            askReadWritePermissions(activity)
    }
}