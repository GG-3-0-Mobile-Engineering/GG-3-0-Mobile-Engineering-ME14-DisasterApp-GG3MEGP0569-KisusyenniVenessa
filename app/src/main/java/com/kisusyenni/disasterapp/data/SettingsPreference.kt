package com.kisusyenni.disasterapp.data

import kotlinx.coroutines.flow.Flow

interface SettingsPreference {
    fun getTheme(): Flow<Boolean>
    suspend fun saveTheme(isDarkMode: Boolean)
}