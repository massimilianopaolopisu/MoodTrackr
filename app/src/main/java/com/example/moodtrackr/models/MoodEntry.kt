package com.example.moodtrackr.models

import java.time.LocalDate

class MoodEntry(
    private var date: LocalDate,
    private var happiness: Int?,
    private var anger: Int?,
    private var love: Int?,
    private var stress: Int?,
    private var energy: Int?,
    private var sleep: Int?,
    private var health: Int?,
    private var depression: Int?,
    private var notes: String?
) {

    var Date: LocalDate
        get() = date
        set(value) {
            date = value
        }

    var Happiness: Int
        get() = clampValue(happiness)
        set(value) {
            happiness = clampValue(value)
        }

    var Anger: Int
        get() = clampValue(anger)
        set(value) {
            anger = clampValue(value)
        }

    var Love: Int
        get() = clampValue(love)
        set(value) {
            love = clampValue(value)
        }

    var Stress: Int
        get() = clampValue(stress)
        set(value) {
            stress = clampValue(value)
        }

    var Energy: Int
        get() = clampValue(energy)
        set(value) {
            energy = clampValue(value)
        }

    var Sleep: Int
        get() = clampValue(sleep)
        set(value) {
            sleep = clampValue(value)
        }

    var Health: Int
        get() = clampValue(health)
        set(value) {
            health = clampValue(value)
        }

    var Depression: Int
        get() = clampValue(depression)
        set(value) {
            depression = clampValue(value)
        }

    var Notes: String?
        get() = notes
        set(value) {
            notes = value ?: ""
        }

    fun getAverage(): Int {
        val values = listOf(Happiness, Anger, Love, Stress, Energy, Sleep, Health, Depression)
        return values.sum() / values.size
    }

    private fun clampValue(value: Int?): Int {
        return when {
            value == null -> 50
            value < 1 -> 1
            value > 100 -> 100
            else -> value
        }
    }
}