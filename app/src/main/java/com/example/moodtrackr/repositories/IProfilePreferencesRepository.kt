package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.Profile

interface IProfilePreferencesRepository: ISave<Profile> {
    fun load() : Profile
}