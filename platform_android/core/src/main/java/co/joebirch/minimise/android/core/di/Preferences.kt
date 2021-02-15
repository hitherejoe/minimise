package co.joebirch.minimise.android.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Preferences @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "settings"
    )
    val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    val accessToken: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }

    suspend fun setAuthToken(token: String) {
        dataStore.edit { settings ->
            settings[KEY_AUTH_TOKEN] = token
        }
    }

    companion object {
        const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"
    }
}