package com.example.datn.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.R
import com.example.datn.core.base.BaseViewModel
import com.example.datn.features.login.repository.LoginRepository
import com.example.datn.utils.Constants
import com.example.datn.utils.extension.RxNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val rxNetwork: RxNetwork
) : BaseViewModel() {

    private val _loginState: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val loginState: LiveData<Boolean> get() = _loginState

    private var user = Constants.EMPTY_STRING
    private var pass = Constants.EMPTY_STRING

    override fun onDidBindViewModel() {

    }

    private fun setStateLogin(state: Boolean) {
        dataManager.setStateLogin(state)
    }

    /**
     * Get password from firebase database
     */
    private fun getPass() {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                compositeDisposable.add(
                    loginRepository.getPass()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { response ->
                            if (response.second != Constants.EMPTY_STRING) {
                                setErrorString(response.second)
                            } else {
                                if (response.first != Constants.EMPTY_STRING) {
                                    if (user == "admin" && pass == response.first!!) {
                                        setStateLogin(true)
                                        _loginState.value = true
                                    } else {
                                        setErrorString(resourcesService.getString(R.string.login_activity_user_invalid))
                                    }
                                }
                            }
                        }
                )
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }

    fun startLogin(user: String, pass: String) {
        this.user = user
        this.pass = pass
        getPass()
    }
}