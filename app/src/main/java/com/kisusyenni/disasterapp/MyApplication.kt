package com.kisusyenni.disasterapp

import android.app.Application
import com.kisusyenni.disasterapp.di.mainViewModelModule
import com.kisusyenni.disasterapp.di.networkModule
import com.kisusyenni.disasterapp.di.settingsViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(networkModule, mainViewModelModule, settingsViewModelModule)
        }
    }
}