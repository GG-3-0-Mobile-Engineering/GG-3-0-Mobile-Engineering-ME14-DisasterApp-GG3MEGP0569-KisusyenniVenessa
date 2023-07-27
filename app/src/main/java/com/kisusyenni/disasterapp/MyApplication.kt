package com.kisusyenni.disasterapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.kisusyenni.disasterapp.di.mainViewModelModule
import com.kisusyenni.disasterapp.di.networkModule
import com.kisusyenni.disasterapp.di.settingsViewModelModule
import com.kisusyenni.disasterapp.utils.NotificationHelper
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
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NotificationHelper.CHANNEL_ID, NotificationHelper.CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT ).apply {
                description = "Alert high water level"
            }
            val notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}