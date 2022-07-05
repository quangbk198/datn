package com.example.datn.features.setting.repository

import com.example.datn.data.model.ThresholdModel
import com.google.firebase.database.DatabaseReference
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

}