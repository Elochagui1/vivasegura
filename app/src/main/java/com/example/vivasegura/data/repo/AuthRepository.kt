package com.example.vivasegura.data.repo

import com.example.vivasegura.core.Security
import com.example.vivasegura.data.db.dao.UserDao
import com.example.vivasegura.data.entities.User

class AuthRepository(private val userDao: UserDao) {

    suspend fun login(username: String, passwordPlain: String): Result<User> {
        val user = userDao.findByUsername(username)
            ?: return Result.failure(Exception("Usuario no existe"))

        val hash = Security.sha256(passwordPlain)
        return if (hash == user.passwordHash) Result.success(user)
        else Result.failure(Exception("Clave incorrecta"))
    }
}
