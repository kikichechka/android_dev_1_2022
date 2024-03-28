package com.example.sixteenthhw.data

import com.example.sixteenthhw.entity.UsefulActivity
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class UsefulActivityDto @Inject constructor(
    @SerializedName("activity") override val activity: String,
    @SerializedName("type") override val type: String,
    @SerializedName("participants") override val participants: Int
) : UsefulActivity
