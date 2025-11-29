package com.example.moodtrackr.models

import android.content.ContentValues
import android.database.Cursor
import com.example.moodtrackr.models.interfaces.IDatabaseModel
import java.time.LocalDate

class MoodEntryCustomMood() : IDatabaseModel<MoodEntryCustomMood> {
    constructor(
        entryDate: LocalDate,
        customMoodId: Long,
        value: Int = 50
    ) : this() {
        _entryDate = entryDate
        _customMoodId = customMoodId
        _value = value
    }

    private var _entryDate: LocalDate = LocalDate.now()
    private var _customMoodId: Long = 0
    private var _value: Int = 50

    companion object {
        const val TABLE_NAME = "mood_entry_custom_moods"
        const val COLUMN_ENTRY_DATE = "entry_date"
        const val COLUMN_CUSTOM_MOOD_ID = "custom_mood_id"
        const val COLUMN_VALUE = "value"

        fun fromCursor(cursor: Cursor): MoodEntryCustomMood {
            val entryDateIndex = cursor.getColumnIndex(COLUMN_ENTRY_DATE)
            val customMoodIdIndex = cursor.getColumnIndex(COLUMN_CUSTOM_MOOD_ID)
            val valueIndex = cursor.getColumnIndex(COLUMN_VALUE)

            val entryDate = if (entryDateIndex >= 0) {
                LocalDate.parse(cursor.getString(entryDateIndex))
            } else {
                LocalDate.now()
            }
            val customMoodId = if (customMoodIdIndex >= 0) cursor.getLong(customMoodIdIndex) else 0L
            val value = if (valueIndex >= 0) cursor.getInt(valueIndex) else 50

            return MoodEntryCustomMood(entryDate, customMoodId, value)
        }
    }

    var entryDate: LocalDate
        get() = _entryDate
        set(value) {
            _entryDate = value
        }

    var customMoodId: Long
        get() = _customMoodId
        set(value) {
            _customMoodId = value
        }

    var value: Int
        get() = _value
        set(v) { _value = v }

    override fun getColumnValue(columnName: String): Any {
        return when (columnName) {
            COLUMN_ENTRY_DATE -> _entryDate.toString()
            COLUMN_CUSTOM_MOOD_ID -> _customMoodId
            COLUMN_VALUE -> _value
            else -> ""
        }
    }

    override fun toContentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ENTRY_DATE, _entryDate.toString())
        contentValues.put(COLUMN_CUSTOM_MOOD_ID, _customMoodId)
        contentValues.put(COLUMN_VALUE, _value)
        return contentValues
    }

    override fun getTableName(): String {
        return TABLE_NAME
    }

    override fun getPrimaryKeyColumnName(): String {
        return COLUMN_ENTRY_DATE
    }

    override fun getContentValues(item: MoodEntryCustomMood): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ENTRY_DATE, item._entryDate.toString())
        contentValues.put(COLUMN_CUSTOM_MOOD_ID, item._customMoodId)
        contentValues.put(COLUMN_VALUE, item._value)
        return contentValues
    }

    override fun getItemFromCursor(cursor: Cursor): MoodEntryCustomMood {
        return fromCursor(cursor)
    }

    override fun getPrimaryKeyValue(item: MoodEntryCustomMood): Any {
        return item._entryDate.toString()
    }
}
