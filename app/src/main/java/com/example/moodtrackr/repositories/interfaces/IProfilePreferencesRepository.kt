package com.example.moodtrackr.repositories.interfaces

import com.example.moodtrackr.models.Profile

interface IProfilePreferencesRepository : ISave<Profile>, ILoad<Profile> {
}