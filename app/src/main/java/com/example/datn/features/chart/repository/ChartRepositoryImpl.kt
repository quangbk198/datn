package com.example.datn.features.chart.repository

import com.example.datn.data.model.TemHumiWrapModel
import com.example.datn.data.remote.FirebaseDatabaseService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : ChartRepository {

    override fun getTemHumiByDay(
        day: Int,
        month: Int,
        year: Int
    ): Observable<Triple<TemHumiWrapModel, String, Boolean>> {
        return firebaseDatabaseService.getTemHumiByDay(day, month, year)
    }
}