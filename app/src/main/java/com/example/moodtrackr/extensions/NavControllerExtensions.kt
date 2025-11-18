package com.example.moodtrackr.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateTo(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    try {
        if (currentDestination?.route != route) {
            navigate(route, builder)
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}