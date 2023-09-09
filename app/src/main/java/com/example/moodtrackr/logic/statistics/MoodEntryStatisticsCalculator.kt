package com.example.moodtrackr.logic.statistics

import com.example.moodtrackr.logic.statistics.interfaces.IStatisticsCalculator
import com.example.moodtrackr.models.IndicatorStats
import com.example.moodtrackr.models.IndicatorValue
import com.example.moodtrackr.models.MoodEntry
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class MoodEntryStatisticsCalculator : IStatisticsCalculator<MoodEntry> {
    private val indicatorListNames = MoodEntry::class.memberProperties
        .filterIsInstance<KProperty1<MoodEntry, Int>>()
        .filter { it.returnType == Int::class.starProjectedType && !it.name.startsWith("_") }
        .map { it.name }


    override fun calculateStats(t: List<MoodEntry>): List<IndicatorStats> {
        val indicatorStatsList = mutableListOf<IndicatorStats>()

        for (fieldName in indicatorListNames) {
            val getter = MoodEntry::class.declaredMemberProperties.find { it.name == fieldName }?.getter
            if (getter != null) {
                val entriesForIndicator = t.mapNotNull { entry ->
                    val value = getter.call(entry) as? Int
                    if (value != null) {
                        IndicatorValue(entry.date, value)
                    } else {
                        null
                    }
                }

                val minEntry = entriesForIndicator.minByOrNull { it.value }
                val maxEntry = entriesForIndicator.maxByOrNull { it.value }
                val average = entriesForIndicator.map { it.value }.average().toInt()

                val minValue = minEntry?.let { IndicatorValue(it.date, it.value) }
                val maxValue = maxEntry?.let { IndicatorValue(it.date, it.value) }

                val indicatorStats = IndicatorStats(minValue, average, maxValue, fieldName)
                indicatorStatsList.add(indicatorStats)
            }
        }

        return indicatorStatsList
    }
}