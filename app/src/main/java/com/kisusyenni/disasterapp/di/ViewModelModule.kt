package com.kisusyenni.disasterapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kisusyenni.disasterapp.data.SettingsPreference
import com.kisusyenni.disasterapp.data.SettingsPreferenceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun providePreference(@ApplicationContext applicationContext: Context): SettingsPreference {
        return SettingsPreferenceImpl(applicationContext.dataStore)
    }
}