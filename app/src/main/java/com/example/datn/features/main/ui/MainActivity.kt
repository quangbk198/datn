package com.example.datn.features.main.ui

import android.os.Build
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityMainBinding
import com.example.datn.features.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.light_blue_1)
        }
    }

    override fun addViewListener() {

    }
}