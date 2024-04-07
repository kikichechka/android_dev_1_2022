package com.example.photosights.data.model

import android.location.Location
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "imageUri")
    val imageUri: String = "",
    @ColumnInfo(name = "location")
    val location: Location? = null
): Parcelable
