package com.example.moodtrackr.repositories

import android.content.Context
import android.util.Log
import com.example.moodtrackr.dataAccess.SharedPreferencesConnector
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.models.SharedPreferencesKeys
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate
import javax.inject.Inject

class ProfilePreferencesRepository @Inject constructor(context: Context) : SharedPreferencesConnector(context), IProfilePreferencesRepository
{
    override fun load() : Profile {
        val profile = Profile("User","","M", LocalDate.MIN)

        try {
            val sharedPreferences = loadPreferences(SharedPreferencesKeys.PROFILE) ?: return profile

            val savedName = sharedPreferences.getString("name", "") ?: ""
            val savedSurname = sharedPreferences.getString("surname", "") ?: ""
            val savedSex = sharedPreferences.getString("sex", "") ?: ""
            val savedBirthday = sharedPreferences.getString("birthday", "") ?: ""

            var sex = savedSex
            var birthday = DateUtilities.getLocalDateFromStringDate(savedBirthday)

            if (savedSex.isBlank())
                sex = profile.sex

            if (savedBirthday.isBlank())
                birthday = profile.birthday

            return Profile(savedName, savedSurname, sex, birthday)
        } catch(ex: Exception) {
            Log.e("ProfilePreferencesRepository", ex.stackTraceToString())
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
            editor.putString("birthday", DateUtilities.getStringDateFromLocalDate(t.birthday))
            editor.apply()
        } catch (ex: Exception) {
            Log.e("ProfilePreferencesRepository", ex.stackTraceToString())
        }
    }
}