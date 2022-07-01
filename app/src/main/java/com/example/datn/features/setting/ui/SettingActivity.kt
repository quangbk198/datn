package com.example.datn.features.setting.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivitySettingBinding
import com.example.datn.features.setting.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySettingBinding =
        ActivitySettingBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }
        }
    }
}