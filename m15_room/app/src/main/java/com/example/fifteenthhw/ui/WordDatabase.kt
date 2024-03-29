package com.example.fifteenthhw.ui

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract fun wordDao() : WordDao
}