package com.kisusyenni.disasterapp.utils

data class Area(val province: String, val code: String)

object AreaHelper {
    val areaList = listOf(
        Area("Aceh", "ID-AC"),
        Area("Bali", "ID-BA"),
        Area("Kep Bangka Belitung","ID-BB"),
        Area("Banten","ID-BT"),
        Area("Bengkulu","ID-BE"),
        Area("Jawa Tengah","ID-JT"),
        Area("Kalimantan Tengah","ID-KT"),
        Area("Sulawesi Tengah","ID-ST"),
        Area("Jawa Timur","ID-JI"),
        Area("Kalimantan Timur","ID-KI"),
        Area("Nusa Tenggara Timur","ID-NT"),
        Area("Gorontalo","ID-GO"),
        Area("DKI Jakarta","ID-JK"),
        Area("Jambi","ID-JA"),
        Area("Lampung","ID-LA"),
        Area("Maluku","ID-MA"),
        Area("Kalimantan Utara","ID-KU"),
        Area("Maluku Utara","ID-MU"),
        Area("Sulawesi Utara","ID-SA"),
        Area("Sumatera Utara","ID-SU"),
        Area("Papua","ID-PA"),
        Area("Riau","ID-RI"),
        Area("Kepulauan Riau","ID-KR"),
        Area("Sulawesi Tenggara","ID-SG"),
        Area("Kalimantan Selatan","ID-KS"),
        Area("Sulawesi Selatan","ID-SN"),
        Area("Sumatera Selatan","ID-SS"),
        Area("DI Yogyakarta","ID-YO"),
        Area("Jawa Barat","ID-JB"),
        Area("Kalimantan Barat","ID-KB"),
        Area("Nusa Tenggara Barat","ID-NB"),
        Area("Papua Barat","ID-PB"),
        Area("Sulawesi Barat","ID-SR"),
        Area("Sumatera Barat","ID-SB")
    )
}