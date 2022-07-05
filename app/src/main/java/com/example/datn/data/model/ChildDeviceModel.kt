package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ChildDeviceModel {
    var model_code: String? = null
    var state: Int? = null
}