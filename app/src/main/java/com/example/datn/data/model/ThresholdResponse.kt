package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ThresholdResponse {
    var mode: Int? = null
    var select: Boolean? = null
    var threshold_down: Int? = null
    var threshold_up: Int? = null
}