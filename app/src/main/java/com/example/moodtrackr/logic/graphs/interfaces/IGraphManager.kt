package com.example.moodtrackr.logic.graphs.interfaces

import com.example.moodtrackr.models.LineDataSetConfiguration
import com.example.moodtrackr.models.MoodEntry
import com.github.mikephil.charting.data.LineData

interface IGraphManager {
    fun getLineData(
    propertyName: String,
    moodEntryList: List<MoodEntry>,
    lineDataSetConfiguration: LineDataSetConfiguration
    ): LineData

    fun getLineData(
        propertyName: String,
        moodEntryList: List<MoodEntry>
    ): LineData
}