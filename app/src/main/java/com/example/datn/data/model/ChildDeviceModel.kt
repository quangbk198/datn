package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by quangnh
 * Date: 30/6/2022
 * Time: 11:01 PM
 * Project datn
 */

@IgnoreExtraProperties
class ChildDeviceModel {
    var model_code: String? = null
    var state: Int? = null
}