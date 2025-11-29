package com.example.moodtrackr.repositories.interfaces

import com.example.moodtrackr.models.CustomMood

interface ICustomMoodsRepository : ISave<CustomMood> {
    fun insertCustomMood(customMood: CustomMood): CustomMood?
    fun getAllCustomMoods(): List<CustomMood>
        fun getCustomMood(id: Long): CustomMood?
        fun deleteCustomMood(id: Long): Boolean
    fun updateCustomMood(customMood: CustomMood): CustomMood?
}
