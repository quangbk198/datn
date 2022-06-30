package com.example.datn.features.userinfo.ui

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityUserInfoBinding
import com.example.datn.features.login.ui.LoginActivity
import com.example.datn.features.userinfo.ui.fragment.ChangePasswordFragment
import com.example.datn.features.userinfo.viewmodel.UserInfoViewModel
import com.example.datn.utils.DialogView
import com.example.datn.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : BaseActivity<ActivityUserInfoBinding, UserInfoViewModel>() {
    override val viewModel: UserInfoViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityUserInfoBinding = ActivityUserInfoBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            rlChangePass.setOnClickListener {
                addFragment(ChangePasswordFragment.newInstance())
            }

            rlLogout.setOnClickListener {
                DialogView.showAlertDialogLogout(this@UserInfoActivity) {
                    viewModel.setStateLogin(false)
                    openActivity(LoginActivity::class.java, Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }

            ivBack.setOnClickListener { onBackPressed() }
        }
    }
}