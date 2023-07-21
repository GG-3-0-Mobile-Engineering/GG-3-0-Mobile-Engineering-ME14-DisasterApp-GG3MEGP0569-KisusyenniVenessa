package com.kisusyenni.disasterapp.data.api

import com.google.gson.annotations.SerializedName

data class FloodGaugesResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class Properties(

	@field:SerializedName("gaugenameid")
	val gaugenameid: String? = null,

	@field:SerializedName("observations")
	val observations: List<ObservationsItem?>? = null,

	@field:SerializedName("gaugeid")
	val gaugeid: String? = null
)

data class ObservationsItem(

	@field:SerializedName("f1")
	val f1: String? = null,

	@field:SerializedName("f2")
	val f2: Int? = null,

	@field:SerializedName("f3")
	val f3: Int? = null,

	@field:SerializedName("f4")
	val f4: String? = null
)

data class Output(

	@field:SerializedName("geometries")
	val geometries: List<GeometriesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Objects(

	@field:SerializedName("output")
	val output: Output? = null
)

data class Transform(

	@field:SerializedName("scale")
	val scale: List<Any?>? = null,

	@field:SerializedName("translate")
	val translate: List<Any?>? = null
)

data class GeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Int?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: Properties? = null
)

data class Result(

	@field:SerializedName("transform")
	val transform: Transform? = null,

	@field:SerializedName("objects")
	val objects: Objects? = null,

	@field:SerializedName("bbox")
	val bbox: List<Any?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("arcs")
	val arcs: List<Any?>? = null
)
