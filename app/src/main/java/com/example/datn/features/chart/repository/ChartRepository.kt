package com.example.datn.features.chart.repository

import com.example.datn.data.model.TemHumiWrapModel
import io.reactivex.Observable
import io.reactivex.Single

interface ChartRepository {

    /**
     * Get data by day
     */
    fun getTemHumiByDay(
        day: Int,
        month: Int,
        year: Int
    ): Observable<Triple<TemHumiWrapModel, String, Boolean>>

}