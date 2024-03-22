package com.example.fifteenthhw

import android.app.Application
import androidx.room.Room
import com.example.fifteenthhw.ui.WordDatabase

class App: Application() {
    lateinit var db: WordDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            WordDatabase::class.java,
            "db"
        ).build()
    }
}