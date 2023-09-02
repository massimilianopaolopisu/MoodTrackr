package com.example.moodtrackr.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.ULocale
import com.example.moodtrackr.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtilities {
    private lateinit var _locale: ULocale
    private lateinit var _format: String

    fun initialize(context: Context) {
        _locale = ULocale.forLanguageTag(context.getString(R.string.date_locale))
        _format = context.getString(R.string.date_format)
    }

    fun getMillisFromStringDate(date: String, pattern: String = _format): Long {
        if(date.isBlank() || !isDateWellFormatted(date, pattern))
            return 0

        return getMillisFromLocalDate(getLocalDateFromStringDate(date, pattern))
    }

    fun getStringDateFromMillis(millis: Long, pattern: String = _format): String {
        return getStringDateFromLocalDate(getLocalDateFromMillis(millis), pattern)
    }

    fun getLocalDateFromStringDate(date: String, pattern: String = _format): LocalDate {
        if(date.isBlank() || !isDateWellFormatted(date, pattern))
            return LocalDate.MIN

        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
    }

    fun getMillisFromLocalDate(date: LocalDate): Long {
        return date.toEpochDay() * (24 * 60 * 60 * 1000)
    }

    fun getStringDateFromLocalDate(date: LocalDate, pattern: String = _format): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun getLocalDateFromMillis(millis: Long): LocalDate {
        return LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateWellFormatted(date: String, format: String): Boolean{
        val sdf = SimpleDateFormat(format, _locale)
        sdf.isLenient = false

        return try {
            sdf.parse(date)
            true
        } catch (ex: Exception) {
            false
        }
    }
}