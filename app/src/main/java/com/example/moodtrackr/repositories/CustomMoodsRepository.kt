package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.dataAccess.GenericSqlWrapper
import com.example.moodtrackr.models.CustomMood
import com.example.moodtrackr.repositories.interfaces.ICustomMoodsRepository
import javax.inject.Inject

class CustomMoodsRepository @Inject constructor(private val context: Context) : GenericSqlWrapper<CustomMood>(context, CustomMood()), ICustomMoodsRepository {

    override fun insertCustomMood(customMood: CustomMood): CustomMood? {
        return insert(customMood)
    }

    override fun save(t: CustomMood) {
        insert(t)
    }

    override fun getAllCustomMoods(): List<CustomMood> {
        return getAll()
    }

override fun getCustomMood(id: Long): CustomMood? {
        return getByPrimaryKey(id)
    }

    override fun deleteCustomMood(id: Long): Boolean {
        return try {
            val db = com.example.moodtrackr.helpers.SqlDatabaseHelper(context).writableDatabase
            db.delete("mood_entry_custom_moods", "custom_mood_id = ?", arrayOf(id.toString()))
            db.close()

            deleteByPrimaryKey(id)
        } catch (ex: Exception) {
            false
        }
    }

    override fun updateCustomMood(customMood: CustomMood): CustomMood? {
        return update(customMood)
    }
}
