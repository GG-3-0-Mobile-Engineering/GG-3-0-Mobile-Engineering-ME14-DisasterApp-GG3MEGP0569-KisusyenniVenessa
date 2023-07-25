package com.kisusyenni.disasterapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferenceImpl(private val dataStore: DataStore<Preferences>): SettingsPreference {
    override fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }
    }

    override suspend fun saveTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDarkMode
        }
    }

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("darkTheme")
    }
}