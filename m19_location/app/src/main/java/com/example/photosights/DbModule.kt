package com.example.photosights

import android.content.Context
import androidx.room.Room
import com.example.photosights.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
        ).addMigrations(MIGRATION_1_2).build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.photoDao()
}
