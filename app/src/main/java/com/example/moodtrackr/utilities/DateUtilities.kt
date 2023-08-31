package com.example.moodtrackr.utilities

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtilities {
    fun getMillisFromStringDate(date: String, pattern: String = "MM/dd/yyyy"): Long {
        return getMillisFromLocalDate(getLocalDateFromStringDate(date, pattern))
    }

    fun getStringDateFromMillis(millis: Long, pattern: String = "MM/dd/yyyy"): String {
        return getStringDateFromLocalDate(getLocalDateFromMillis(millis), pattern)
    }

    fun getLocalDateFromStringDate(date: String, pattern: String = "MM/dd/yyyy"): LocalDate {
        if(date.isBlank())
            return LocalDate.MIN

        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))

    }

    fun getMillisFromLocalDate(date: LocalDate): Long {
        return date.toEpochDay() * (24 * 60 * 60 * 1000)
    }

    fun getStringDateFromLocalDate(date: LocalDate, pattern: String = "MM/dd/yyyy"): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun getLocalDateFromMillis(millis: Long): LocalDate {
        return LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
    }
}