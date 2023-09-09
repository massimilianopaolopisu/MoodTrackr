package com.example.moodtrackr.repositories.interfaces

import com.example.moodtrackr.models.ApplicationPreferences

interface IApplicationPreferencesRepository : ISave<ApplicationPreferences>,
    ILoad<ApplicationPreferences>