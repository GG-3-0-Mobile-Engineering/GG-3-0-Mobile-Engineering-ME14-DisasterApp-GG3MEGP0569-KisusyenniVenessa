package com.kisusyenni.disasterapp.data.api

import com.google.gson.annotations.SerializedName

data class ReportsResponse(

	@field:SerializedName("result")
	val result: ReportsResult? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class ReportsOutput(

	@field:SerializedName("geometries")
	val geometries: List<ReportsGeometriesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class ReportsResult(

	@field:SerializedName("objects")
	val objects: ReportsObjects? = null,

	@field:SerializedName("bbox")
	val bbox: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("arcs")
	val arcs: List<Any?>? = null
)

data class ReportsData(

	@field:SerializedName("condition")
	val condition: Int? = null,

	@field:SerializedName("report_type")
	val reportType: String? = null,

	@field:SerializedName("accessabilityFailure")
	val accessabilityFailure: Int? = null,

	@field:SerializedName("structureFailure")
	val structureFailure: Int? = null
)

data class ReportsObjects(

	@field:SerializedName("output")
	val output: ReportsOutput? = null
)

data class ReportsGeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: ReportsProperties? = null
)

data class Tags(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String? = null,

	@field:SerializedName("district_id")
	val districtId: Any? = null,

	@field:SerializedName("local_area_id")
	val localAreaId: String? = null,

	@field:SerializedName("region_code")
	val regionCode: String? = null
)

data class ReportsProperties(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("disaster_type")
	val disasterType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tags")
	val tags: Tags? = null,

	@field:SerializedName("partner_icon")
	val partnerIcon: Any? = null,

	@field:SerializedName("report_data")
	val reportData: ReportsData? = null,

	@field:SerializedName("pkey")
	val pkey: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("partner_code")
	val partnerCode: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
