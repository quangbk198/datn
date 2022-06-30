package com.example.datn.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.DataRealtime
import com.example.datn.features.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _realtimeData: MutableLiveData<DataRealtime> by lazy { MutableLiveData() }

    val realtimeData: LiveData<DataRealtime> get() = _realtimeData

    override fun onDidBindViewModel() {
        getRealtimeDataTemAndHumi()
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
}