package com.example.datn.features.main.repository

import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.TemHumiWrapModel
import io.reactivex.Observable
import io.reactivex.Single

interface MainRepository {

    /**
     * Get temperature and humidity realtime data
     */
    fun getRealtimeDataTemAndHumi(): Observable<TemHumiWrapModel>

    /**
     * Get list child device
     */
    fun getListChildDevice(): Observable<Triple<ChildDeviceModel, String, String>>

    /**
     * Turn on/off child device
     */
    fun turnOnOffChildDevice(modelCode: String, state: Int) : Single<Int>
}