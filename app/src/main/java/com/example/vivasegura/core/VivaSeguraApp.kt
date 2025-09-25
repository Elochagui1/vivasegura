package com.example.vivasegura.core

import android.app.Application
import androidx.room.Room
import com.example.vivasegura.data.db.VivaSeguraDatabase

class VivaSeguraApp : Application() {
    companion object {
        lateinit var db: VivaSeguraDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            VivaSeguraDatabase::class.java,
            "vivasegura.db"
        )
            .addCallback(VivaSeguraDatabase.prepopulateCallback())
            .fallbackToDestructiveMigration()
            .build()
    }
}
