package com.example.moodtrackr.repositories

import com.example.moodtrackr.models.ApplicationPreferences

interface IApplicationPreferencesRepository : ISave<ApplicationPreferences>, ILoad<ApplicationPreferences> {
}