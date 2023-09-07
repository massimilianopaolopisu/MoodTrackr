package com.example.moodtrackr.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.ULocale
import android.util.Log
import com.example.moodtrackr.R
import com.example.moodtrackr.enums.TimeFrame
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtilities {
    private lateinit var _locale: ULocale
    private lateinit var _format: String
    private val _defaultDate = LocalDate.of(1970, 1, 1)
    private val _defaultMillis = _defaultDate.toEpochDay() * (24 * 60 * 60 * 1000)

    fun initialize(context: Context) {
        _locale = ULocale.forLanguageTag(context.getString(R.string.date_locale))
        _format = context.getString(R.string.date_format)
    }

    fun getMillisFromStringDate(date: String?, pattern: String = _format): Long {
        if (date.isNullOrBlank() || !isDateWellFormatted(date, pattern))
            return getMillisFromLocalDate(LocalDate.now())

        return getMillisFromLocalDate(getLocalDateFromStringDate(date, pattern))
    }

    fun getStringDateFromMillis(millis: Long, pattern: String = _format): String {
        return getStringDateFromLocalDate(getLocalDateFromMillis(millis), pattern)
    }

    fun getLocalDateFromStringDate(date: String?, pattern: String = _format): LocalDate {
        if (date.isNullOrBlank() || !isDateWellFormatted(date, pattern))
            return LocalDate.MIN

        return try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
        } catch (ex: Exception) {
            Log.e("DateUtilities.getLocalDateFromStringDate", ex.stackTraceToString())
            _defaultDate
        }
    }

    fun getMillisFromLocalDate(date: LocalDate): Long {
        return try {
            date.toEpochDay() * (24 * 60 * 60 * 1000)
        } catch (ex: Exception) {
            Log.e("DateUtilities.getMillisFromLocalDate", ex.stackTraceToString())
            _defaultMillis
        }
    }

    fun getStringDateFromLocalDate(date: LocalDate, pattern: String = _format): String {
        return try {
            date.format(DateTimeFormatter.ofPattern(pattern))
        } catch (ex: Exception) {
            Log.e("DateUtilities.getStringDateFromLocalDate", ex.stackTraceToString())
            getDefaultStringDate(pattern)
        }
    }

    fun getLocalDateFromMillis(millis: Long): LocalDate {
        return try {
            LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
        } catch (ex: Exception) {
            Log.e("DateUtilities.getLocalDateFromMillis", ex.stackTraceToString())
            _defaultDate
        }
    }

    fun getStartDateFromTimeFrame(timeFrame: TimeFrame): LocalDate {
        val today = LocalDate.now()

        val startDate = when (timeFrame) {
            TimeFrame.LastWeek -> today.minusWeeks(1)
            TimeFrame.LastMonth -> today.minusMonths(1)
            TimeFrame.LastYear -> today.minusYears(1)
            TimeFrame.AllTime -> LocalDate.MIN
        }

        return startDate
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateWellFormatted(date: String, format: String): Boolean{
        return try {
            val sdf = SimpleDateFormat(format, _locale)
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (ex: Exception) {
            Log.e("DateUtilities.isDateWellFormatted", ex.stackTraceToString())
            false
        }
    }

    private fun getDefaultStringDate(pattern: String = _format): String
    {
        return try{
            _defaultDate.format(DateTimeFormatter.ofPattern(pattern))
        } catch (ex: Exception) {
            Log.e("DateUtilities", ex.stackTraceToString())
            _defaultDate.format(DateTimeFormatter.ofPattern(_format))
        }
    }
}