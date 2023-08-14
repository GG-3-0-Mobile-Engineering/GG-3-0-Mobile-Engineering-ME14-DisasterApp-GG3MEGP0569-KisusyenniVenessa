package com.kisusyenni.disasterapp.utils

import com.kisusyenni.disasterapp.data.api.ReportsData
import com.kisusyenni.disasterapp.data.api.ReportsGeometriesItem
import com.kisusyenni.disasterapp.data.api.ReportsObjects
import com.kisusyenni.disasterapp.data.api.ReportsOutput
import com.kisusyenni.disasterapp.data.api.ReportsProperties
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.data.api.ReportsResult
import com.kisusyenni.disasterapp.data.api.Tags

object DummyData {
    fun generateReports(): ReportsResponse {
        val geometries = ArrayList<ReportsGeometriesItem>()
        for (i in 0 until 10) {
            val geometry = ReportsGeometriesItem(
                type = "Point",
                properties = ReportsProperties(
                    pkey = "322063",
                    createdAt = "2023-08-14T04:28:22.146Z",
                    source = "grasp",
                    status = "confirmed",
                    url = "2b3f657a-95f8-4433-b2ff-7806b142b734",
                    imageUrl = null,
                    disasterType = "wind",
                    reportData = ReportsData(
                        reportType = "wind"
                    ),
                    tags = Tags(
                        districtId = null,
                        regionCode = "3374",
                        localAreaId = null,
                        instanceRegionCode = "ID-JT"
                    ),
                    title = "Judul bencana",
                    text = "Deskripsi bencana",
                    partnerCode = null,
                    partnerIcon = null,
                ),
                coordinates = listOf( 110.3283895445, -6.982661065)
            )
            geometries.add(geometry)
        }
        return ReportsResponse(
            statusCode = 200,
            result = ReportsResult(
                type = "Topology",
                objects = ReportsObjects(
                    output = ReportsOutput(
                        type = "GeometryCollection",
                        geometries = geometries
                    )
                ),
                arcs = listOf(),
                bbox = listOf( 98.5086025623, -10.261501799, 123.6149547349, 3.6351699829)
            )
        )
    }
}