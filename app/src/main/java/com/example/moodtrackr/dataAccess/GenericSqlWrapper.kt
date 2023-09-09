package com.example.moodtrackr.dataAccess

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.models.interfaces.IDatabaseModel

open class GenericSqlWrapper<T: IDatabaseModel<T>>(context: Context, private val databaseModel: IDatabaseModel<T>) {

    private val _sqlDatabaseHelper = SqlDatabaseHelper(context)

    fun insert(item: T): T? {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase
            val values = item.getContentValues(item)

            val newRowId = db.insertWithOnConflict(
                item.getTableName(),
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
            )

            val cursor = getCursor(item.getPrimaryKeyColumnName(), newRowId)

            if (!cursor.moveToFirst())
                return null

            val insertedItem = item.getItemFromCursor(cursor)
            db.close()

            insertedItem
        } catch (ex: Exception) {
            Log.e("GenericRepository.insert", ex.stackTraceToString())
            null
        }
    }

    fun getAll(): List<T> {
        val itemList = mutableListOf<T>()

        try {
            val db = _sqlDatabaseHelper.readableDatabase
            val cursor = db.query(
                databaseModel.getTableName(),
                null,
                null,
                null,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {
                try {
                    itemList.add(databaseModel.getItemFromCursor(cursor))
                } catch (ex: Exception)
                {
                    Log.e("GenericRepository.getAll", ex.stackTraceToString())
                }
            }

            cursor.close()
            db.close()
        } catch (ex: Exception) {
            Log.e("GenericRepository.getAll", ex.stackTraceToString())
        }

        return itemList
    }

    fun getByPrimaryKey(primaryKeyValue: Any): T? {
        return try {
            val cursor = getCursor(databaseModel.getPrimaryKeyColumnName(), primaryKeyValue)

            if (!cursor.moveToFirst()) {
                cursor.close()
                return null
            }

            val item = databaseModel.getItemFromCursor(cursor)
            cursor.close()
            item
        } catch (ex: Exception) {
            Log.e("GenericRepository.getByPrimaryKey", ex.stackTraceToString())
            null
        }
    }

    fun getItemsWithCustomSelection(selection: String, selectionArgs: Array<String>): List<T> {
        val itemList = mutableListOf<T>()

        try {
            val db = _sqlDatabaseHelper.readableDatabase
            val cursor = db.query(
                databaseModel.getTableName(),
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {
                itemList.add(databaseModel.getItemFromCursor(cursor))
            }

            cursor.close()
            db.close()
        } catch (ex: Exception) {
            Log.e("GenericRepository.getItemsWithCustomSelection", ex.stackTraceToString())
        }

        return itemList
    }

    fun deleteByPrimaryKey(primaryKeyValue: Any): Boolean {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase
            val selection = "${databaseModel.getPrimaryKeyColumnName()} = ?"
            val selectionArgs = arrayOf(primaryKeyValue.toString())

            val rowsDeleted = db.delete(databaseModel.getTableName(), selection, selectionArgs)
            db.close()

            rowsDeleted > 0
        } catch (ex: Exception) {
            Log.e("GenericRepository.deleteByPrimaryKey", ex.stackTraceToString())
            false
        }
    }

    fun update(item: T): T? {
        return try {
            val db = _sqlDatabaseHelper.writableDatabase
            val values = item.getContentValues(item)

            val selection = "${item.getPrimaryKeyColumnName()} = ?"
            val selectionArgs = arrayOf(item.getPrimaryKeyValue(item).toString())

            val updatedRows = db.update(
                item.getTableName(),
                values,
                selection,
                selectionArgs
            )

            if(updatedRows <= 0)
                return null

            val updatedItem = item.getItemFromCursor(
                getCursor(item.getPrimaryKeyColumnName(), item.getPrimaryKeyValue(item))
            )

            db.close()
            updatedItem
        } catch (ex: Exception) {
            Log.e("GenericRepository.update", ex.stackTraceToString())
            null
        }
    }

    private fun getCursor(columnName: String, value: Any): Cursor {
        val db = _sqlDatabaseHelper.readableDatabase
        val selection = "$columnName = ?"
        val selectionArgs = arrayOf(value.toString())

        return db.query(
            databaseModel.getTableName(),
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }
}
