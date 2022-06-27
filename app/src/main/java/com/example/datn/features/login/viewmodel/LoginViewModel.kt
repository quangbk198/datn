package com.example.datn.features.login.viewmodel

import com.example.datn.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : BaseViewModel() {

    override fun onDidBindViewModel() {

    }

    fun setStateLogin(state: Boolean) {
        dataManager.setStateLogin(state)
    }
}