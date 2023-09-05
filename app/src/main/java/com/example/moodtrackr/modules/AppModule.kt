package com.example.moodtrackr.modules

import android.content.Context
import com.example.moodtrackr.dataImportExport.DataImporterExporterStrategy
import com.example.moodtrackr.dataImportExport.SharedPreferencesImporterExporterStrategy
import com.example.moodtrackr.dataImportExport.SqlMoodEntriesImporterExporterStrategy
import com.example.moodtrackr.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.repositories.ApplicationPreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
import com.example.moodtrackr.repositories.MoodEntriesRepository
import com.example.moodtrackr.repositories.ProfilePreferencesRepository
import com.example.moodtrackr.repositories.ThemePreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideIApplicationPreferencesRepository(context: Context): IApplicationPreferencesRepository {
        return ApplicationPreferencesRepository(context)
    }

    @Provides
    @Singleton
    fun provideIProfilePreferencesRepository(context: Context): IProfilePreferencesRepository {
        return ProfilePreferencesRepository(context)
    }

    @Provides
    @Singleton
    fun provideIMoodEntriesRepository(context: Context): IMoodEntriesRepository {
        return MoodEntriesRepository(context)
    }

    @Provides
    @Singleton
    fun provideIThemePreferencesRepository(context: Context): IThemePreferencesRepository {
        return ThemePreferencesRepository(context)
    }

    @Provides
    @Singleton
    fun provideIDataImporterExporterStrategy(context: Context): IDataImporterExporterStrategy {
        val strategies = listOf(
            SharedPreferencesImporterExporterStrategy(context),
            SqlMoodEntriesImporterExporterStrategy(context)
        )
        return DataImporterExporterStrategy(strategies)
    }
}