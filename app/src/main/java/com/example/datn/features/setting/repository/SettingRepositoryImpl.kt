package com.example.datn.features.setting.repository

import com.example.datn.data.model.OutputConditionModel
import com.example.datn.data.model.OutputConditionResponse
import com.example.datn.data.model.ThresholdModel
import com.example.datn.data.model.ThresholdResponse
import com.example.datn.data.remote.FirebaseDatabaseService
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : SettingRepository{

    override fun setThreshold(
        thresholdModel: ThresholdModel,
        ref: DatabaseReference
    ): Single<Int> {
        return firebaseDatabaseService.setThreshold(thresholdModel, ref)
    }

    override fun setOutputCondition(
        outputCondition: OutputConditionModel,
        ref: DatabaseReference
    ): Single<Int> {
        return firebaseDatabaseService.setOutputCondition(outputCondition, ref)
    }

    override fun getTemperatureThreshold(): Observable<ThresholdResponse> {
        return firebaseDatabaseService.getTemperatureThreshold()
    }

    override fun getHumidityThreshold(): Observable<ThresholdResponse> {
        return firebaseDatabaseService.getHumidityThreshold()
    }

    override fun getStateLightDevice(): Observable<OutputConditionResponse> {
        return firebaseDatabaseService.getStateLightDevice()
    }

    override fun getStatePumpDevice(): Observable<OutputConditionResponse> {
        return firebaseDatabaseService.getStatePumpDevice()

    }
}