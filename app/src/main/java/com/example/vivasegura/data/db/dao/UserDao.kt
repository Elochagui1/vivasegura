package com.example.vivasegura.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vivasegura.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :u LIMIT 1")
    suspend fun findByUsername(u: String): User?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)
}
