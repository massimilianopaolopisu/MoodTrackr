package com.example.moodtrackr.models

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerSelectableDates(private var dateSet: Set<LocalDate>) : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val date = DateUtilities.getLocalDateFromMillis(utcTimeMillis)

        return dateSet.contains(date)
    }
}