package com.example.moodtrackr.models

import java.time.LocalDate

class MoodEntry() {
    private val _defaultValue = 50
    private var _date: LocalDate = LocalDate.now()
    private var _happiness: Int? = _defaultValue
    private var _anger: Int? = _defaultValue
    private var _love: Int? = _defaultValue
    private var _stress: Int? = _defaultValue
    private var _energy: Int? = _defaultValue
    private var _sleep: Int? = _defaultValue
    private var _health: Int? = _defaultValue
    private var _depression: Int? = _defaultValue
    private var _notes: String? = ""

    constructor(
        date: LocalDate,
        happiness: Int,
        anger: Int,
        love: Int,
        stress: Int,
        energy: Int,
        sleep: Int,
        health: Int,
        depression: Int,
        notes: String
    ) : this() {
        _date = date
        _happiness = happiness
        _anger = anger
        _love = love
        _stress = stress
        _energy = energy
        _sleep = sleep
        _health = health
        _depression = depression
        _notes = notes
    }

    var date: LocalDate
        get() = _date
        set(value) {
            _date = value
        }

    var happiness: Int
        get() = clampValue(_happiness)
        set(value) {
            _happiness = clampValue(value)
        }

    var anger: Int
        get() = clampValue(_anger)
        set(value) {
            _anger = clampValue(value)
        }

    var love: Int
        get() = clampValue(_love)
        set(value) {
            _love = clampValue(value)
        }

    var stress: Int
        get() = clampValue(_stress)
        set(value) {
            _stress = clampValue(value)
        }

    var energy: Int
        get() = clampValue(_energy)
        set(value) {
            _energy = clampValue(value)
        }

    var sleep: Int
        get() = clampValue(_sleep)
        set(value) {
            _sleep = clampValue(value)
        }

    var health: Int
        get() = clampValue(_health)
        set(value) {
            _health = clampValue(value)
        }

    var depression: Int
        get() = clampValue(_depression)
        set(value) {
            _depression = clampValue(value)
        }

    var notes: String
        get() = _notes?: ""
        set(value) {
            _notes = value
        }

    fun getAverage(): Int {
        val values = listOf(happiness, anger, love, stress, energy, sleep, health, depression)
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