package com.example.moodtrackr.adapters

import com.example.moodtrackr.utilities.DateUtilities
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.time.LocalDate

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: LocalDate?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(DateUtilities.getStringDateFromLocalDate(value))
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): LocalDate? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        val dateString = `in`.nextString()
        return DateUtilities.getLocalDateFromStringDate(dateString)
    }
}