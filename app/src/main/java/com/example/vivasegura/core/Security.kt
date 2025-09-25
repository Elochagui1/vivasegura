package com.example.vivasegura.core

import java.security.MessageDigest

object Security {
    fun sha256(text: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(text.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}
