package com.cyalc.jakesrepos.commons

import android.arch.persistence.room.TypeConverter
import java.util.*


object Converters {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? = value?.let {
        return Date(it)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
