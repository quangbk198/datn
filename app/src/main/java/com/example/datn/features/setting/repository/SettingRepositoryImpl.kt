package com.example.datn.features.setting.repository

import com.example.datn.data.model.ThresholdModel
import com.example.datn.data.remote.FirebaseDatabaseService
import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by quangnh
 * Date: 5/7/2022
 * Time: 11:56 PM
 * Project datn
 */
class SettingRepositoryImpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : SettingRepository{

    override fun setThreshold(
        thresholdModel: ThresholdModel,
        ref: DatabaseReference
    ): Single<Int> {
        return firebaseDatabaseService.setThreshold(thresholdModel, ref)
    }
}