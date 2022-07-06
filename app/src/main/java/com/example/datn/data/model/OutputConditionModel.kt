package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class OutputConditionModel(select: Boolean, state: Int) {
    var select: Boolean? = select
    var state: Int? = state
}