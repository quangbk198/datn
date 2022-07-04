package com.example.datn.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.TemHumiWrapModel
import com.example.datn.features.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _realtimeData: MutableLiveData<TemHumiWrapModel> by lazy { MutableLiveData() }

    private val _childDevice: MutableLiveData<Pair<ChildDeviceModel, String>> by lazy { MutableLiveData() }

    val realtimeData: LiveData<TemHumiWrapModel> get() = _realtimeData

    val childDevice: LiveData<Pair<ChildDeviceModel, String>> get() = _childDevice

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
                .subscribe { data ->
                    when (data.second) {
                        "get", "update" -> {
                            _childDevice.value = Pair(data.first, data.second)
                        }

                        "error" -> {
                            setErrorString(data.third)
                        }
                    }
                }
        )
    }
}