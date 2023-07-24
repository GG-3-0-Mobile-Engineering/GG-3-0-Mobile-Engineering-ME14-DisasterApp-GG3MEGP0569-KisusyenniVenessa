package com.kisusyenni.disasterapp.utils

import com.kisusyenni.disasterapp.R

object DisasterTypeHelper {
    enum class DisasterType {
        FLOOD,
        EARTHQUAKE,
        FIRE,
        HAZE,
        WIND,
        VOLCANO
    }

    fun generateDisasterStrId(type: DisasterType): Int {
        return when(type) {
            DisasterType.FLOOD -> R.string.flood
            DisasterType.EARTHQUAKE -> R.string.earthquake
            DisasterType.FIRE -> R.string.fire
            DisasterType.HAZE -> R.string.haze
            DisasterType.WIND -> R.string.wind
            DisasterType.VOLCANO -> R.string.volcano
        }
    }
}