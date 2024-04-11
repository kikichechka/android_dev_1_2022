package com.example.photosights.data

import android.location.Location
import androidx.room.TypeConverter

class MyConverters {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return "${location.provider},${location.latitude},${location.longitude}"
    }

    @TypeConverter
    fun toLocation(str: String?) : Location? {
        if (str != null) {
            val list = str.split(",")
            val location = Location(list[0])
            location.latitude = list[1].toDouble()
            location.longitude = list[2].toDouble()
            return location
        }
        return null
    }
}
