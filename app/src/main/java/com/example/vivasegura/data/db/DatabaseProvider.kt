package com.example.vivasegura.data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile private var INSTANCE: VivaSeguraDatabase? = null

    fun get(context: Context): VivaSeguraDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                VivaSeguraDatabase::class.java,
                "vivasegura.db"
            )
                .addCallback(VivaSeguraDatabase.prepopulateCallback())
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}
