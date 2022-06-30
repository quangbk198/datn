package com.example.datn.features.main.repository

import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.DataRealtimeTemHumi
import io.reactivex.Observable

interface MainRepository {

    /**
     * Get temperature and humidity realtime data
     */
    fun getRealtimeDataTemAndHumi(): Observable<DataRealtimeTemHumi>

    /**
     * Get list child device
     */
    fun getListChildDevice(): Observable<ChildDeviceModel>
}