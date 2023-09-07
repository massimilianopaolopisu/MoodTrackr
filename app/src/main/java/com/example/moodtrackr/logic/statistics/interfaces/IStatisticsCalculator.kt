package com.example.moodtrackr.logic.statistics.interfaces

import com.example.moodtrackr.models.IndicatorStats

interface IStatisticsCalculator<T> {
    fun calculateStats(t: List<T>): List<IndicatorStats>
}