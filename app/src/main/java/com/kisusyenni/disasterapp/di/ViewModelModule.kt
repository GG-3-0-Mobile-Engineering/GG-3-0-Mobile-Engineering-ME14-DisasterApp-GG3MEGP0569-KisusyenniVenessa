package com.kisusyenni.disasterapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kisusyenni.disasterapp.data.SettingsPreference
import com.kisusyenni.disasterapp.data.SettingsPreferenceImpl
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepositoryImpl
import com.kisusyenni.disasterapp.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val viewModelModule= module {
    single<DisasterAppRepository> {
        DisasterAppRepositoryImpl(get())
    }

    single<SettingsPreference> {
        SettingsPreferenceImpl(androidContext().dataStore)
    }

    single {
        MainViewModel(get(), get())
    }
}