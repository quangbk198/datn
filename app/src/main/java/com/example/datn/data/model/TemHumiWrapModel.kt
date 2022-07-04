package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class TemHumiWrapModel {
    var time: Int? = null
    var humi: Double? = null
    var tem: Double? = null
}