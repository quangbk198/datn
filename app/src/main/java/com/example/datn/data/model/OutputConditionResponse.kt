package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by quangnh
 * Date: 26/7/2022
 * Time: 10:54 PM
 * Project datn
 */
@IgnoreExtraProperties
class OutputConditionResponse {
    var select: Boolean? = null
    var state: Int? = null
}