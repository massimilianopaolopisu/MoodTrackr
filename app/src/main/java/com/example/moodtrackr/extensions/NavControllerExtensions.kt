package com.example.moodtrackr.extensions

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateTo(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    val currentDestination = this.currentBackStackEntry?.destination?.route
    val isCurrentDestinationResumed = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

    if (currentDestination != route && isCurrentDestinationResumed) {
        try {
            navigate(route, builder)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}