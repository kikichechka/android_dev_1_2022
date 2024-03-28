package com.example.seventeenthhw.data

import android.os.Parcelable
import com.example.seventeenthhw.data.model.Mars
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMars(
    val photos: List<Mars>,
) : Parcelable
