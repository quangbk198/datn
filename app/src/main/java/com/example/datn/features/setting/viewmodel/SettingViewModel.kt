package com.example.datn.features.setting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.R
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.OutputConditionModel
import com.example.datn.data.model.ThresholdModel
import com.example.datn.features.setting.repository.SettingRepository
import com.example.datn.utils.extension.RxNetwork
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val firebaseDatabase: FirebaseDatabase,
    private val rxNetwork: RxNetwork
) : BaseViewModel() {

    private val _updateThresholdSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val updateThresholdSuccess: LiveData<Boolean> get() = _updateThresholdSuccess

    var thresholdTemMode: Int = 1

    var thresholdHumiMode: Int = 1

    var thresholdDownTem: Int = 0

    var thresholdDownHumi: Int = 0

    var thresholdUpTem: Int = 30

    var thresholdUpHumi: Int = 30

    /**
     * State of light:
     * 0: off
     * 1: on
     */
    var stateLight = 0

    /**
     * State of pump:
     * 0: off
     * 1: on
     */
    var statePump = 0

    override fun onDidBindViewModel() {

    }

    fun setThresholdOnFirebase(
        isSelectedTem: Boolean,
        isSelectedHumi: Boolean,
        isSelectedLight: Boolean,
        isSelectedPump: Boolean
    ) {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                setLoading(true)

                val thresholdTemModel = ThresholdModel(thresholdTemMode, isSelectedTem, thresholdDownTem, thresholdUpTem)
                val refTem = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/threshold/input/tem")

                val thresholdHumiModel = ThresholdModel(thresholdHumiMode, isSelectedHumi, thresholdDownHumi, thresholdUpHumi)
                val refHumi = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/threshold/input/humi")

                val conditionLightModel = OutputConditionModel(isSelectedLight, stateLight)
                val refConditionLight = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/threshold/output/light")

                val conditionPumpModel = OutputConditionModel(isSelectedPump, statePump)
                val refConditionPump = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/threshold/output/pump")

                compositeDisposable.add(
                    Single.zip(
                        settingRepository.setThreshold(thresholdTemModel, refTem),
                        settingRepository.setThreshold(thresholdHumiModel, refHumi),
                        settingRepository.setOutputCondition(conditionLightModel, refConditionLight),
                        settingRepository.setOutputCondition(conditionPumpModel, refConditionPump)
                    ) { resultTem, resultHumi, resultLight, resultPump->
                        return@zip (resultTem == 1) && (resultHumi == 1) && (resultLight == 1) && (resultPump == 1)

                    }.subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { result ->
                            setLoading(false)

                            _updateThresholdSuccess.value = result
                        }
                )
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }
}