package com.example.seventeenthhw.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mars(
    @SerializedName("img_src") val imgSrc: String,
    @SerializedName("earth_date") val earthDate: String,
    val rover: Rover,
    val camera: Camera,
    val sol: Int
) : Parcelable

@Parcelize
data class Rover(
    val name: String
) : Parcelable

@Parcelize
data class Camera(
    val name: String
) : Parcelable
