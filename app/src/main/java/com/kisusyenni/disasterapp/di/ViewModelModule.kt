package com.kisusyenni.disasterapp.di

import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepositoryImpl
import com.kisusyenni.disasterapp.viewmodel.MainViewModel
import org.koin.dsl.module

val viewModelModule= module {
    single<DisasterAppRepository> {
        DisasterAppRepositoryImpl(get())
    }

    single {
        MainViewModel(get())
    }
}