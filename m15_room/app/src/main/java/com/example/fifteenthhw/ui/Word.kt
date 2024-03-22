package com.example.fifteenthhw.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "meaning")
    val meaning: String,
    @ColumnInfo(name = "count")
    val count: Int
)
