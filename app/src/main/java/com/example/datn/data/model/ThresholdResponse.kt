package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by quangnh
 * Date: 26/7/2022
 * Time: 10:53 PM
 * Project datn
 */
@IgnoreExtraProperties
class ThresholdResponse {
    var mode: Int? = null
    var select: Boolean? = null
    var threshold_down: Int? = null
    var threshold_up: Int? = null
}