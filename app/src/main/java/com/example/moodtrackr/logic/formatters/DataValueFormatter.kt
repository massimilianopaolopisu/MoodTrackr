package com.example.moodtrackr.logic.formatters

import com.example.moodtrackr.utilities.DateUtilities
import com.github.mikephil.charting.formatter.ValueFormatter

class DateValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return DateUtilities.getStringDateFromMillis(value.toLong())
    }
}