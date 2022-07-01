package com.example.datn.features.chart.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityChartBinding
import com.example.datn.features.chart.viewmodel.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartActivity : BaseActivity<ActivityChartBinding, ChartViewModel>() {
    override val viewModel: ChartViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityChartBinding =
        ActivityChartBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }
        }
    }
}