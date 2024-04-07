package com.example.photosights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photosights.data.model.Photo

@Database(entities = [Photo::class], version = 2)
@TypeConverters(MyConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}
