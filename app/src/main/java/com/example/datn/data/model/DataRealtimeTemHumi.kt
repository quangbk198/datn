package com.example.datn.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class DataRealtimeTemHumi {
    var humi: Float? = null
    var tem: Float? = null
}