package com.example.moodtrackr.viewModels

import androidx.lifecycle.ViewModel
import com.example.moodtrackr.repositories.IApplicationPreferencesRepository
import com.example.moodtrackr.repositories.IMoodEntriesRepository
import com.example.moodtrackr.repositories.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.IThemePreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val applicationPreferencesRepository: IApplicationPreferencesRepository,
    val profilePreferencesRepository: IProfilePreferencesRepository,
    val moodEntriesRepository: IMoodEntriesRepository,
    val themePreferencesRepository: IThemePreferencesRepository
) : ViewModel() {
}