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

    @GET("reports/archive")
    suspend fun getReportsArchive(
        @Query("admin") admin: String? = null,
        @Query("start") format: String? = "2017-12-04T00:00:00+0700",
        @Query("end") disaster: String? = "2017-12-06T05:00:00+0700",
        @Query("geoformat") geoformat: String? = null,
    ): ReportsResponse
}