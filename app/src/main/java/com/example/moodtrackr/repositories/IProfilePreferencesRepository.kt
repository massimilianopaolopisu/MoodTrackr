package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.Profile

interface IProfilePreferencesRepository {
    fun load() : Profile
    fun save(profile : Profile)
}