package com.example.moodtrackr.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.models.SharedPreferencesKeys

class ProfilePreferencesRepository(private val context: Context) : IProfilePreferencesRepository
{
    override fun load() : Profile {
        val sharedPreferences = loadPreferences()
        val savedName = sharedPreferences.getString("name", "") ?: ""
        val savedSurname = sharedPreferences.getString("surname", "") ?: ""
        val savedSex = sharedPreferences.getString("sex", "") ?: ""
        val savedBirthday = sharedPreferences.getString("birthday", "") ?: ""

        var sex = savedSex
        var birthday = savedBirthday

        if (savedSex.isBlank())
            sex = "M"

        if (savedBirthday.isBlank())
            birthday = "01/01/1990"

        return Profile(savedName, savedSurname, sex, birthday)
    }

    override fun save(profile: Profile) {
        val sharedPreferences = loadPreferences()
        val editor = sharedPreferences.edit()

        editor.putString("name", profile.name)
        editor.putString("surname", profile.surname)
        editor.putString("sex", profile.sex)
        editor.putString("birthday", profile.birthday)
        editor.apply()
    }

    private fun loadPreferences(): SharedPreferences {
        return context.getSharedPreferences(SharedPreferencesKeys.PROFILE, Context.MODE_PRIVATE)
    }
}