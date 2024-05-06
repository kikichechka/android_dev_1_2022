package com.example.seventeenthhw.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstronomyPicture(
    val date: String,
    val explanation: String,
    val title: String,
    val url: String
) : Parcelable
