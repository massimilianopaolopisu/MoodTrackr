package com.example.moodtrackr.viewModels

import androidx.lifecycle.ViewModel
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.logic.statistics.interfaces.IStatisticsCalculator
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val applicationPreferencesRepository: IApplicationPreferencesRepository,
    val profilePreferencesRepository: IProfilePreferencesRepository,
    val moodEntriesRepository: IMoodEntriesRepository,
    val themePreferencesRepository: IThemePreferencesRepository,
    val dataImporterExporterStrategy: IDataImporterExporterStrategy,
    val moodEntryStatisticsCalculator: IStatisticsCalculator<MoodEntry>
) : ViewModel() {
}