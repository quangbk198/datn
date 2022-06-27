package com.example.datn.features.splash.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivitySplashBinding
import com.example.datn.features.login.ui.LoginActivity
import com.example.datn.features.main.ui.MainActivity
import com.example.datn.features.splash.viewmodel.SplashViewModel
import com.example.datn.utils.extension.delayFunction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.isLogin.observe(this) { isLogin ->
            delayFunction(3000) {
                if (isLogin) {
                    openActivity(MainActivity::class.java)
                } else {
                    openActivity(LoginActivity::class.java)
                }

                finish()
            }
        }
    }
}