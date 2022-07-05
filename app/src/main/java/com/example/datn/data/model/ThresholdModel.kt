package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ThresholdModel(mode: Int, select: Boolean, threshold_down: Int, threshold_up: Int) {
    var mode: Int? = mode
    var select: Boolean? = select
    var threshold_down: Int? = threshold_down
    var threshold_up: Int? = threshold_up
}