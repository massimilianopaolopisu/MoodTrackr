package com.example.moodtrackr.modules

import android.content.Context
import com.example.moodtrackr.dataAccess.MediaStoreCompatIO
import com.example.moodtrackr.dataAccess.interfaces.IFileSystemIO
import com.example.moodtrackr.logic.dataImportExport.DataImporterExporterStrategy
import com.example.moodtrackr.logic.dataImportExport.SharedPreferencesImporterExporterStrategy
import com.example.moodtrackr.logic.dataImportExport.SqlMoodEntriesImporterExporterStrategy
import com.example.moodtrackr.logic.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.logic.statistics.MoodEntryStatisticsCalculator
import com.example.moodtrackr.logic.statistics.interfaces.IStatisticsCalculator
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.ApplicationPreferencesRepository
import com.example.moodtrackr.repositories.MoodEntriesRepository
import com.example.moodtrackr.repositories.ProfilePreferencesRepository
import com.example.moodtrackr.repositories.ThemePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
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
    fun provideIDataImporterExporterStrategy(context: Context, fileSystemIO: IFileSystemIO): IDataImporterExporterStrategy {
        val strategies = listOf(
            SharedPreferencesImporterExporterStrategy(context, fileSystemIO),
            SqlMoodEntriesImporterExporterStrategy(context, fileSystemIO)
        )
        return DataImporterExporterStrategy(strategies)
    }

    @Provides
    @Singleton
    fun provideIStatisticsCalculator(): IStatisticsCalculator<MoodEntry> {
        return MoodEntryStatisticsCalculator()
    }

    @Provides
    @Singleton
    fun provideIFileSystemIO(): IFileSystemIO {
        return MediaStoreCompatIO()
    }
}