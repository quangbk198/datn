package com.example.datn.features.userinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.R
import com.example.datn.core.base.BaseViewModel
import com.example.datn.features.userinfo.repository.UserInfoRepository
import com.example.datn.utils.Constants
import com.example.datn.utils.extension.RxNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by quangnh
 * Date: 27/6/2022
 * Time: 11:42 PM
 * Project datn
 */

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val rxNetwork: RxNetwork
) : BaseViewModel() {

    private val _messageUpdatePass: MutableLiveData<String> by lazy { MutableLiveData() }

    val messageUpdatePass: LiveData<String> get() = _messageUpdatePass

    override fun onDidBindViewModel() {

    }

    /**
     * Change password
     */
    fun changePass(oldPass: String, newPass: String) {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                compositeDisposable.add(
                    userInfoRepository.getPass()
                        .flatMap { pass ->
                            if (pass.first != oldPass) {
                                return@flatMap Single.create<String> { emitter ->
                                    emitter.onSuccess(resourcesService.getString(R.string.change_pass_fragment_old_pass_incorrect))
                                }
                            } else {
                                return@flatMap userInfoRepository.updatePass(newPass)
                            }
                        }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { message ->
                            _messageUpdatePass.value = message
                        }
                )
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }

    fun setStateLogin(state: Boolean) {
        dataManager.setStateLogin(state)
    }
}