package com.example.moodtrackr.models.interfaces

import android.content.ContentValues
import android.database.Cursor

interface IDatabaseModel<T> {
    fun getColumnValue(columnName: String): Any?
    fun toContentValues(): ContentValues
    fun getTableName(): String
    fun getPrimaryKeyColumnName(): String
    fun getContentValues(item: T): ContentValues
    fun getItemFromCursor(cursor: Cursor): T
    fun getPrimaryKeyValue(item: T): Any
}