package com.kisusyenni.disasterapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("floodgauges")
    suspend fun getFloodGauges(
        @Query("admin") admin: String? = null
    ): FloodGaugesResponse

    @GET("reports")
    suspend fun getReports(
        @Query("admin") admin: String? = null
    ): ReportsResponse
}