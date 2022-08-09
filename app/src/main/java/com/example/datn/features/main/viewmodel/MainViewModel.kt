package com.example.datn.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.R
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.TemHumiWrapModel
import com.example.datn.features.main.repository.MainRepository
import com.example.datn.utils.extension.RxNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val rxNetwork: RxNetwork
) : BaseViewModel() {

    private val _realtimeData: MutableLiveData<TemHumiWrapModel> by lazy { MutableLiveData() }

    private val _childDevice: MutableLiveData<Pair<ChildDeviceModel, String>> by lazy { MutableLiveData() }

    private val _turnOnOffDevice: MutableLiveData<Pair<Boolean, ChildDeviceModel>> by lazy { MutableLiveData() }

    val realtimeData: LiveData<TemHumiWrapModel> get() = _realtimeData

    val childDevice: LiveData<Pair<ChildDeviceModel, String>> get() = _childDevice

    val turnOnOffDevice: LiveData<Pair<Boolean, ChildDeviceModel>> get() = _turnOnOffDevice

    override fun onDidBindViewModel() {
        getRealtimeDataTemAndHumi()
        getListDevice()
    }

    private fun getRealtimeDataTemAndHumi() {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                compositeDisposable.add(
                    mainRepository.getRealtimeDataTemAndHumi()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { data ->
                            _realtimeData.value = data
                        }
                )
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }

    }

    private fun getListDevice() {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
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
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }

    fun turnOnOffDevice(device: ChildDeviceModel) {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                setLoading(true)

                if (device.model_code != null && device.state != null) {
                    compositeDisposable.add(
                        mainRepository.turnOnOffChildDevice(device.model_code!!, device.state!!)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe({ result ->
                                setLoading(false)
                                _turnOnOffDevice.value = Pair(result == 1, device)
                            }, {
                                setLoading(false)
                            })
                    )
                }
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }
}