package com.example.moodtrackr.repositories

import android.content.Context
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.models.SharedPreferencesKeys

class ProfilePreferencesRepository(context: Context) : SharedPreferencesRepository(context), IProfilePreferencesRepository
{
    override fun load() : Profile {
        val profile = Profile("User","","M","1990-01-01")

        try {
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.PROFILE) ?: return profile

            val savedName = sharedPreferences.getString("name", "") ?: ""
            val savedSurname = sharedPreferences.getString("surname", "") ?: ""
            val savedSex = sharedPreferences.getString("sex", "") ?: ""
            val savedBirthday = sharedPreferences.getString("birthday", "") ?: ""

            var sex = savedSex
            var birthday = savedBirthday

            if (savedSex.isBlank())
                sex = profile.sex

            if (savedBirthday.isBlank())
                birthday = profile.birthday

            return Profile(savedName, savedSurname, sex, birthday)
        } catch(ex: Exception) {
            // TODO _logger.error(ex)
            return profile
        }
    }

    override fun save(t: Profile) {
        try{
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.PROFILE) ?: return
            val editor = sharedPreferences.edit()

            editor.putString("name", t.name)
            editor.putString("surname", t.surname)
            editor.putString("sex", t.sex)
            editor.putString("birthday", t.birthday)
            editor.apply()
        } catch (ex: Exception) {
            // TODO _logger.error(ex)
        }
    }
}