package com.example.datn.features.login.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityLoginBinding
import com.example.datn.features.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {

    }
}