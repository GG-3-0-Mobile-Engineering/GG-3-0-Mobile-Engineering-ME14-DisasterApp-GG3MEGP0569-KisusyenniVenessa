package com.kisusyenni.disasterapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: DisasterAppRepository) : ViewModel() {
    fun getData(): Flow<ReportsResponse> {
        return repository.getReports()
    }
}