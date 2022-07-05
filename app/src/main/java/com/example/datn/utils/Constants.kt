package com.example.datn.utils

object Constants {
    const val SHARED_PREFERENCE_KEY = "shared_key"
    const val KEY_BUNDLE = "KEY_BUNDLE"
    const val EMPTY_STRING = ""

    enum class SharePrefKey(val key: String) {
        StateLogin("state_login")
    }

    enum class DeviceModel(val modelCode: String) {
        Light("light"),
        Pump("pump")
    }

    enum class TypeCalendar {
        DATE,
        MONTH
    }

    enum class ThresholdMode(val mode: Int) {
        LOWER(0),
        BETWEEN(1),
        HIGHER(2)
    }
}