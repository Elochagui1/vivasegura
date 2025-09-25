package com.example.vivasegura.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "vivasegura_prefs")

class SessionManager(private val context: Context) {
    private object Keys {
        val LOGGED_IN: Preferences.Key<Boolean> = booleanPreferencesKey("logged_in")
        val USERNAME: Preferences.Key<String> = stringPreferencesKey("username")
    }

    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.LOGGED_IN] ?: false }

    val username: Flow<String> =
        context.dataStore.data.map { it[Keys.USERNAME] ?: "" }

    suspend fun saveLogin(username: String) {
        context.dataStore.edit {
            it[Keys.LOGGED_IN] = true
            it[Keys.USERNAME] = username
        }
    }

    suspend fun logout() {
        context.dataStore.edit {
            it[Keys.LOGGED_IN] = false
            it[Keys.USERNAME] = ""
        }
    }
}
