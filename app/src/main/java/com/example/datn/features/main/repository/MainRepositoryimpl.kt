package com.example.datn.features.main.repository

import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.DataRealtimeTemHumi
import com.example.datn.data.remote.FirebaseDatabaseService
import com.google.firebase.database.*
import io.reactivex.Observable
import javax.inject.Inject

class MainRepositoryimpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : MainRepository {

    override fun getRealtimeDataTemAndHumi(): Observable<DataRealtimeTemHumi> {
        return firebaseDatabaseService.getRealtimeDataTemAndHumi()
    }

    override fun getListChildDevice(): Observable<ChildDeviceModel> {
        return firebaseDatabaseService.getListChildDevice()
    }
}