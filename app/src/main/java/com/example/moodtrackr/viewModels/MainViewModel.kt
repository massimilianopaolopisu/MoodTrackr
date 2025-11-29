package com.example.moodtrackr.viewModels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.logic.graphs.interfaces.IGraphManager
import com.example.moodtrackr.logic.statistics.interfaces.IStatisticsCalculator
import com.example.moodtrackr.models.ApplicationPreferences
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.repositories.interfaces.ICustomMoodsRepository
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val context: Context,
    val applicationPreferencesRepository: IApplicationPreferencesRepository,
    val profilePreferencesRepository: IProfilePreferencesRepository,
    val moodEntriesRepository: IMoodEntriesRepository,
    val customMoodsRepository: ICustomMoodsRepository,
    val moodEntryCustomMoodsRepository: com.example.moodtrackr.repositories.MoodEntryCustomMoodsRepository,
    val themePreferencesRepository: IThemePreferencesRepository,
    val dataImporterExporterStrategy: IDataImporterExporterStrategy,
    val moodEntryStatisticsCalculator: IStatisticsCalculator<MoodEntry>,
    val graphManager: IGraphManager
) : ViewModel() {
    var themePreferences: ThemePreferences = ThemePreferences()
    var applicationPreferences: ApplicationPreferences = ApplicationPreferences()
    var profile: Profile = Profile()
    var mainActivity: Activity? = null
}
