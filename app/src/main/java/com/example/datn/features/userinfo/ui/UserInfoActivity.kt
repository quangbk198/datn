package com.example.datn.features.userinfo.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityUserInfoBinding
import com.example.datn.features.userinfo.ui.fragment.ChangePasswordFragment
import com.example.datn.features.userinfo.viewmodel.UserInfoViewModel
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
                showToast("okokokok")
            }
        }
    }
}