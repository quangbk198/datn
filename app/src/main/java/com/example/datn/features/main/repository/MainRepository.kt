package com.example.datn.features.main.repository

import com.example.datn.data.model.DataRealtime
import io.reactivex.Observable
import io.reactivex.Single

interface MainRepository {

    /**
     * Get temperature and humidity realtime data
     */
    fun getRealtimeDataTemAndHumi(): Observable<DataRealtime>
}