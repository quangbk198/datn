package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class OutputConditionResponse {
    var select: Boolean? = null
    var state: Int? = null
}