package com.kisusyenni.disasterapp.utils

import com.kisusyenni.disasterapp.data.api.ReportsData
import com.kisusyenni.disasterapp.data.api.ReportsGeometriesItem
import com.kisusyenni.disasterapp.data.api.ReportsProperties
import com.kisusyenni.disasterapp.data.api.Tags

object DummyDisaster {
    fun generateDisaster(): List<ReportsGeometriesItem> {
        val geometries = ArrayList<ReportsGeometriesItem>()
        geometries.add(
            ReportsGeometriesItem(
                coordinates = listOf(),
                type = "",
                ReportsProperties(
                    imageUrl = "",
                    disasterType = "",
                    createdAt = "",
                    source = "",
                    title = "",
                    url="",
                    Tags(
                        instanceRegionCode = "",
                        districtId = "",
                        localAreaId = "",
                        regionCode = "",
                    ),
                    ReportsData(
                        condition = 0,
                        reportType = "",
                        accessabilityFailure = 0,
                        structureFailure = 0
                    ),
                    pkey = "",
                    partnerCode = "",
                    status = ""

                )
            )
        )
        return geometries
    }
}