package com.example.moodtrackr.extensions

fun String?.capitalizeFirstLetter(): String? {
    if (isNullOrBlank()) return this

    return substring(0, 1).uppercase() + substring(1)
}