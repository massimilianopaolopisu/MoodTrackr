package com.example.moodtrackr.models

import android.content.ContentValues
import android.database.Cursor
import com.example.moodtrackr.models.interfaces.IDatabaseModel
import java.time.LocalDate

class MoodEntry() : IDatabaseModel<MoodEntry> {
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

    private val _defaultValue = 50
    private val  _minValue = 1
    private val _maxValue = 100
    private val _primaryKey = COLUMN_DATE
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

    companion object {
        const val TABLE_NAME = "mood_entries"
        const val COLUMN_DATE = "date"
        const val COLUMN_HAPPINESS = "happiness"
        const val COLUMN_ANGER = "anger"
        const val COLUMN_LOVE = "love"
        const val COLUMN_STRESS = "stress"
        const val COLUMN_ENERGY = "energy"
        const val COLUMN_SLEEP = "sleep"
        const val COLUMN_HEALTH = "health"
        const val COLUMN_DEPRESSION = "depression"
        const val COLUMN_NOTES = "notes"

        fun fromCursor(cursor: Cursor): MoodEntry {
            if (!cursor.moveToFirst())
                return MoodEntry()

            val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
            val happinessIndex = cursor.getColumnIndex(COLUMN_HAPPINESS)
            val angerIndex = cursor.getColumnIndex(COLUMN_ANGER)
            val loveIndex = cursor.getColumnIndex(COLUMN_LOVE)
            val stressIndex = cursor.getColumnIndex(COLUMN_STRESS)
            val energyIndex = cursor.getColumnIndex(COLUMN_ENERGY)
            val sleepIndex = cursor.getColumnIndex(COLUMN_SLEEP)
            val healthIndex = cursor.getColumnIndex(COLUMN_HEALTH)
            val depressionIndex = cursor.getColumnIndex(COLUMN_DEPRESSION)
            val notesIndex = cursor.getColumnIndex(COLUMN_NOTES)

            val date = if (dateIndex >= 0) LocalDate.parse(cursor.getString(dateIndex)) else LocalDate.now()
            val happiness = if (happinessIndex >= 0) cursor.getInt(happinessIndex) else 0
            val anger = if (angerIndex >= 0) cursor.getInt(angerIndex) else 0
            val love = if (loveIndex >= 0) cursor.getInt(loveIndex) else 0
            val stress = if (stressIndex >= 0) cursor.getInt(stressIndex) else 0
            val energy = if (energyIndex >= 0) cursor.getInt(energyIndex) else 0
            val sleep = if (sleepIndex >= 0) cursor.getInt(sleepIndex) else 0
            val health = if (healthIndex >= 0) cursor.getInt(healthIndex) else 0
            val depression = if (depressionIndex >= 0) cursor.getInt(depressionIndex) else 0
            val notes = if (notesIndex >= 0) cursor.getString(notesIndex) else ""

            return MoodEntry(
                date,
                happiness,
                anger,
                love,
                stress,
                energy,
                sleep,
                health,
                depression,
                notes
            )
        }
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

    fun getOverallIndicator(): Int {
        val values = listOf(
            happiness,
            getOpposite(anger),
            love,
            getOpposite(stress),
            energy,
            sleep,
            health,
            getOpposite(depression)
        )
        return values.sum() / values.size
    }

    override fun getColumnValue(columnName: String): Any {
        return when (columnName) {
            COLUMN_DATE -> date
            COLUMN_HAPPINESS -> happiness
            COLUMN_ANGER -> anger
            COLUMN_LOVE -> love
            COLUMN_STRESS -> stress
            COLUMN_ENERGY -> energy
            COLUMN_SLEEP -> sleep
            COLUMN_HEALTH -> health
            COLUMN_DEPRESSION -> depression
            COLUMN_NOTES -> notes

            else -> ""
        }
    }

    override fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_DATE, date.toString())
        values.put(COLUMN_HAPPINESS, happiness)
        values.put(COLUMN_ANGER, anger)
        values.put(COLUMN_LOVE, love)
        values.put(COLUMN_STRESS, stress)
        values.put(COLUMN_ENERGY, energy)
        values.put(COLUMN_SLEEP, sleep)
        values.put(COLUMN_HEALTH, health)
        values.put(COLUMN_DEPRESSION, depression)
        values.put(COLUMN_NOTES, notes)

        return values
    }

    override fun getTableName(): String {
        return TABLE_NAME
    }

    override fun getPrimaryKeyColumnName(): String {
        return _primaryKey
    }

    override fun getContentValues(item: MoodEntry): ContentValues {
        return item.toContentValues()
    }

    override fun getItemFromCursor(cursor: Cursor): MoodEntry {
        return fromCursor(cursor)
    }

    override fun getPrimaryKeyValue(item: MoodEntry): Any {
        return item.getColumnValue(_primaryKey)
    }

    private fun clampValue(value: Int?): Int {
        return when {
            value == null -> _defaultValue
            value < _minValue -> _minValue
            value > _maxValue -> _maxValue
            else -> value
        }
    }

    private fun getOpposite(value: Int): Int{
        return (_maxValue + 1) - value
    }
}