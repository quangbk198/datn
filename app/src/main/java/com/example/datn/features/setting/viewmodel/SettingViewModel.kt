package com.example.datn.features.setting.viewmodel

import com.example.datn.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

) : BaseViewModel() {

    var thresholdModeTem: Int = 1

    var thresholdModeHumi: Int = 1

    var thresholdTemValue1: Int = 0

    var thresholdHumiValue1: Int = 0

    var thresholdTemValue2: Int = 30

    var thresholdHumiValue2: Int = 30

    override fun onDidBindViewModel() {

    }
}