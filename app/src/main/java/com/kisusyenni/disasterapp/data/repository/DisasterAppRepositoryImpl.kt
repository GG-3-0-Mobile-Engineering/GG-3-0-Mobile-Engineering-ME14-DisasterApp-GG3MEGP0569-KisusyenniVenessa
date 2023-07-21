package com.kisusyenni.disasterapp.data.repository

import com.kisusyenni.disasterapp.data.api.ApiService
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DisasterAppRepositoryImpl(private val api: ApiService): DisasterAppRepository {
    override fun getFloodGauges() = flow {
        val data = api.getFloodGauges()
        emit(data)
    }
    override fun getReports(): Flow<ReportsResponse> = flow {
        val data = api.getReports()
        emit(data)
    }
}