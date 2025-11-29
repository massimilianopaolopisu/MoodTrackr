package com.example.moodtrackr.models

import android.content.ContentValues
import android.database.Cursor
import androidx.compose.ui.graphics.Color
import com.example.moodtrackr.models.interfaces.IDatabaseModel

class CustomMood() : IDatabaseModel<CustomMood> {
    constructor(
        id: Long = 0,
        name: String,
        colorValue: Long = Color.Gray.value.toLong()
    ) : this() {
        _id = id
        _name = name
        _colorValue = colorValue
    }

    private var _id: Long = 0
    private var _name: String = ""
    private var _colorValue: Long = Color.Gray.value.toLong()
    

    companion object {
        const val TABLE_NAME = "custom_moods"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COLOR = "color_value"
        

        fun fromCursor(cursor: Cursor): CustomMood {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
            val colorIndex = cursor.getColumnIndex(COLUMN_COLOR)

            val id = if (idIndex >= 0) cursor.getLong(idIndex) else 0L
            val name = if (nameIndex >= 0) cursor.getString(nameIndex) else ""
            val rawColor = if (colorIndex >= 0) cursor.getLong(colorIndex) else Color.Gray.value.toLong()
            val colorValue = if (rawColor and 0xFF000000L == 0L) (rawColor or 0xFF000000L) else rawColor
            return CustomMood(id, name, colorValue)
        }
    }

    var id: Long
        get() = _id
        set(value) {
            _id = value
        }

    var name: String
        get() = _name
        set(value) {
            _name = value
        }

    var colorValue: Long
        get() = _colorValue
        set(value) {
            _colorValue = value
        }

    val color: Color
        get() = Color(_colorValue.toULong())

    override fun getColumnValue(columnName: String): Any? {
        return when (columnName) {
            COLUMN_ID -> _id
            COLUMN_NAME -> _name
            COLUMN_COLOR -> _colorValue
            else -> null
        }
    }

    override fun toContentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, _name)
        contentValues.put(COLUMN_COLOR, (_colorValue or 0xFF000000L))
        
        return contentValues
    }

    override fun getTableName(): String {
        return TABLE_NAME
    }

    override fun getPrimaryKeyColumnName(): String {
        return COLUMN_ID
    }

    override fun getContentValues(item: CustomMood): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, item._name)
        contentValues.put(COLUMN_COLOR, item._colorValue)
        
        return contentValues
    }

    override fun getItemFromCursor(cursor: Cursor): CustomMood {
        return fromCursor(cursor)
    }

    override fun getPrimaryKeyValue(item: CustomMood): Any {
        return item._id
    }
}
