package com.kisusyenni.disasterapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("floodgauges")
    suspend fun getFloodGauges(
        @Query("admin") admin: String? = null
    ): FloodGaugesResponse

    @GET("reports")
    suspend fun getReports(
        @Query("admin") admin: String? = null,
        @Query("format") format: String? = null,
        @Query("disaster") disaster: String? = null,
        @Query("geoformat") geoformat: String? = null,
        @Query("timeperiod") timeperiod: String? = null
    ): ReportsResponse

    @GET("reports")
    suspend fun getReports(
        @Query("disaster") disaster: String? = null,
        @Query("timeperiod") timeperiod: String? = "604800"
    ): ReportsResponse

    @GET("reports")
    suspend fun getReports(
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null,
        @Query("timeperiod") timeperiod: String? = "604800"
    ): ReportsResponse
}