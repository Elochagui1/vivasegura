package com.example.vivasegura.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vivasegura.data.db.dao.UserDao
import com.example.vivasegura.data.entities.User

@Database(
    entities = [User::class],
    version = 2, // ⬅️ subimos versión para crear el índice único
    exportSchema = false
)
abstract class VivaSeguraDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val ADMIN_HASH =
            "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918" // "admin"

        fun prepopulateCallback() = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(
                    """
                    INSERT INTO users (id, username, passwordHash, createdAt)
                    VALUES (NULL, 'admin', '$ADMIN_HASH', strftime('%s','now')*1000)
                    """.trimIndent()
                )
            }
        }
    }
}
