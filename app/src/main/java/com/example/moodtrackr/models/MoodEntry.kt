package com.example.moodtrackr.models

import java.time.LocalDate

class MoodEntry(var date: LocalDate, var happiness: Int, var love: Int,
    var energy: Int, var health: Int, var notes: String) {

    fun getAverage(): Int {
        return (happiness + love + energy + health) / 4
    }
}