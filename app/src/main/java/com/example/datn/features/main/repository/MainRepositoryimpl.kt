package com.example.datn.features.main.repository

import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.TemHumiWrapModel
import com.example.datn.data.remote.FirebaseDatabaseService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryimpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : MainRepository {

    override fun getRealtimeDataTemAndHumi(): Observable<TemHumiWrapModel> {
        return firebaseDatabaseService.getRealtimeDataTemAndHumi()
    }

    override fun getListChildDevice(): Observable<Triple<ChildDeviceModel, String, String>> {
        return firebaseDatabaseService.getListChildDevice()
    }

    override fun turnOnOffChildDevice(modelCode: String, state: Int): Single<Int> {
        return firebaseDatabaseService.turnOnOffChildDevice(modelCode, state)
    }
}