package com.example.kuclubapp

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object DataStoreManager {
    private val TOKEN_KEY = stringPreferencesKey("key_token")
    private val AUTO_LOGIN_KEY = booleanPreferencesKey("key_auto_login")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[TOKEN_KEY] = token
        }
    }

    fun getToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[TOKEN_KEY]
        }
    }

    suspend fun saveAutoLoginStatus(context: Context, isChecked: Boolean) {
        context.dataStore.edit { settings ->
            settings[AUTO_LOGIN_KEY] = isChecked
        }
    }

    fun getAutoLoginStatus(context: Context): Flow<Boolean?> {
        return context.dataStore.data.map { settings ->
            settings[AUTO_LOGIN_KEY]
        }
    }

    suspend fun deleteToken(context: Context) {
        context.dataStore.edit { settings ->
            settings.remove(TOKEN_KEY)
        }
    }

}
