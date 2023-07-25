package com.kisusyenni.disasterapp.data.repository

import com.kisusyenni.disasterapp.data.api.FloodGaugesResponse
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import kotlinx.coroutines.flow.Flow

interface DisasterAppRepository {
    fun getFloodGauges(): Flow<FloodGaugesResponse>
    fun getReports(disaster: String): Flow<ReportsResponse>
    fun getReports(disaster: String, admin: String): Flow<ReportsResponse>
}