package com.kisusyenni.disasterapp.data

import com.kisusyenni.disasterapp.data.api.ApiService
import com.kisusyenni.disasterapp.data.api.FloodGaugesResponse
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.utils.DummyData

class FakeApiService: ApiService {
    override suspend fun getFloodGauges(admin: String?): FloodGaugesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getReports(
        admin: String?,
        format: String?,
        disaster: String?,
        geoformat: String?,
        timeperiod: String?
    ): ReportsResponse {
        return DummyData.generateReports()
    }

    override suspend fun getReports(disaster: String?, timeperiod: String?): ReportsResponse {
        return DummyData.generateReports()
    }

    override suspend fun getReports(
        admin: String?,
        disaster: String?,
        timeperiod: String?
    ): ReportsResponse {
        return DummyData.generateReports()
    }

    override suspend fun getReportsArchive(
        admin: String?,
        format: String?,
        disaster: String?,
        geoformat: String?
    ): ReportsResponse {
        TODO("Not yet implemented")
    }
}