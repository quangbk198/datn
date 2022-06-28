package com.example.datn.features.login.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityLoginBinding
import com.example.datn.features.login.viewmodel.LoginViewModel
import com.example.datn.features.main.ui.MainActivity
import com.example.datn.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                val user = edtUsername.text.toString().trim()
                val pass = edtPassword.text.toString().trim()

                if (user.isBlank() && pass.isBlank()) {
                    showToast(getString(R.string.login_activity_please_enter_username_and_pass))
                } else if (user.isBlank()) {
                    showToast(getString(R.string.login_activity_please_enter_username))
                } else if (pass.isBlank()) {
                    showToast(getString(R.string.login_activity_please_enter_pass))
                } else {
                    onLoading(true)
                    viewModel.startLogin(user, pass)
                }
            }
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.apply {
            loginState.observe(this@LoginActivity) { success ->
                if (success) {
                    onLoading(false)
                    openActivity(MainActivity::class.java)
                    finish()
                }
            }
        }
    }
}