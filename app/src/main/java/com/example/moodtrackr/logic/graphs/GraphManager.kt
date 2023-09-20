package com.example.moodtrackr.logic.graphs

import androidx.compose.ui.graphics.toArgb
import com.example.moodtrackr.extensions.capitalizeFirstLetter
import com.example.moodtrackr.logic.graphs.interfaces.IGraphManager
import com.example.moodtrackr.models.LineDataSetConfiguration
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.utilities.DateUtilities
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraphManager: IGraphManager {
    override fun getLineData(
        propertyName: String,
        moodEntryList: List<MoodEntry>,
        lineDataSetConfiguration: LineDataSetConfiguration
    ): LineData {
        val entries = mutableListOf<Entry>()

        moodEntryList.forEachIndexed { _, moodEntry ->
            val value = moodEntry.getValueFromPropertyName(propertyName) ?: 0
            val dateInMillis = DateUtilities.getMillisFromLocalDate(moodEntry.date)
            entries.add(Entry(dateInMillis.toFloat(), value.toFloat()))
        }

        val dataSet = LineDataSet(entries, propertyName.capitalizeFirstLetter())
        dataSet.color = lineDataSetConfiguration.lineColor.toArgb()
        dataSet.lineWidth = lineDataSetConfiguration.lineWidth
        dataSet.setCircleColor(lineDataSetConfiguration.circleColor.toArgb())
        dataSet.setDrawValues(lineDataSetConfiguration.drawValuesEnabled)

        return LineData(dataSet)
    }

    override fun getLineData(
        propertyName: String,
        moodEntryList: List<MoodEntry>
    ): LineData {
        return getLineData(propertyName, moodEntryList, LineDataSetConfiguration())
    }
}