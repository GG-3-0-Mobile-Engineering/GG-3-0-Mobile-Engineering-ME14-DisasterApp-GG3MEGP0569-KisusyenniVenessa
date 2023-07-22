package com.kisusyenni.disasterapp.data.repository

import com.kisusyenni.disasterapp.data.api.FloodGaugesResponse
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import kotlinx.coroutines.flow.Flow

interface DisasterAppRepository {
    fun getFloodGauges(): Flow<FloodGaugesResponse>
    fun getReports(type: String?): Flow<ReportsResponse>
}