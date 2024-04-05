package com.example.photosights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.photosights.model.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}
