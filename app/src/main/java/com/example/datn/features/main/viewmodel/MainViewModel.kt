package com.example.datn.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.DataRealtimeTemHumi
import com.example.datn.features.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _realtimeData: MutableLiveData<DataRealtimeTemHumi> by lazy { MutableLiveData() }

    private val _childDevice: MutableLiveData<ChildDeviceModel> by lazy { MutableLiveData() }

    val realtimeData: LiveData<DataRealtimeTemHumi> get() = _realtimeData

    val childDevice: LiveData<ChildDeviceModel> get() = _childDevice

    override fun onDidBindViewModel() {
        getRealtimeDataTemAndHumi()
        getListDevice()
    }

    private fun getRealtimeDataTemAndHumi() {
        compositeDisposable.add(
            mainRepository.getRealtimeDataTemAndHumi()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { data ->
                    _realtimeData.value = data
                }
        )
    }

    private fun getListDevice() {
        compositeDisposable.add(
            mainRepository.getListChildDevice()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { device ->
                    _childDevice.value = device
                }
        )
    }
}