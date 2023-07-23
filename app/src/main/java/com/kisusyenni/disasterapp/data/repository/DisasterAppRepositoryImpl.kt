package com.kisusyenni.disasterapp.data.repository

import com.kisusyenni.disasterapp.data.api.ApiService
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DisasterAppRepositoryImpl(private val api: ApiService): DisasterAppRepository {
    override fun getFloodGauges() = flow {
        val data = api.getFloodGauges()
        emit(data)
    }.flowOn(Dispatchers.IO)
    override fun getReports(type: String?): Flow<ReportsResponse> = flow {
        val data = api.getReportsArchive(type)
        emit(data)
    }.flowOn(Dispatchers.IO)
}