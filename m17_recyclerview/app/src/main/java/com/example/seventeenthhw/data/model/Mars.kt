package com.example.seventeenthhw.data.model

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
