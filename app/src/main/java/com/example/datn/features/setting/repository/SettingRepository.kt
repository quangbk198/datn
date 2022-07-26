package com.example.datn.features.setting.repository

import com.example.datn.data.model.OutputConditionModel
import com.example.datn.data.model.OutputConditionResponse
import com.example.datn.data.model.ThresholdModel
import com.example.datn.data.model.ThresholdResponse
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by quangnh
 * Date: 5/7/2022
 * Time: 11:55 PM
 * Project datn
 */
interface SettingRepository {

    /**
     * Set threshold
     */
    fun setThreshold(
        thresholdModel: ThresholdModel,
        ref: DatabaseReference
    ) : Single<Int>

    /**
     * Set output condition
     */
    fun setOutputCondition(
        outputCondition: OutputConditionModel,
        ref: DatabaseReference
    ) : Single<Int>

    /**
     * Get temperature threshold
     */
    fun getTemperatureThreshold(): Observable<ThresholdResponse>

    /**
     * Get humidity threshold
     */
    fun getHumidityThreshold(): Observable<ThresholdResponse>

    /**
     * Get state light device
     */
    fun getStateLightDevice(): Observable<OutputConditionResponse>

    /**
     * Get state pump device
     */
    fun getStatePumpDevice(): Observable<OutputConditionResponse>
}