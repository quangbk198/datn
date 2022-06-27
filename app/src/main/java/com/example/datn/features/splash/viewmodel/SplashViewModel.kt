package com.example.datn.features.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel() {

    private val _isLogin: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val isLogin: LiveData<Boolean> get() = _isLogin

    override fun onDidBindViewModel() {
        checkLogin()
    }

    /**
     * Check app login
     */
    private fun checkLogin() {
        _isLogin.value = dataManager.getStateLogin()
    }
}