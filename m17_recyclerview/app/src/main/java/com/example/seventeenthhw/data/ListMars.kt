package com.example.seventeenthhw.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMars(
    val photos: List<Mars>,
) : Parcelable
