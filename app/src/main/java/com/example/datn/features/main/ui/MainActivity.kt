package com.example.datn.features.main.ui

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityMainBinding
import com.example.datn.features.chart.ui.ChartActivity
import com.example.datn.features.main.ui.adapter.DeviceAdapter
import com.example.datn.features.main.viewmodel.MainViewModel
import com.example.datn.features.setting.ui.SettingActivity
import com.example.datn.features.userinfo.ui.UserInfoActivity
import com.example.datn.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    @Inject
    lateinit var deviceAdapter: DeviceAdapter

    private var isUserCloseApp: Boolean = false

    override val viewModel: MainViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.green_dark)
        }

        binding.rcvDevice.apply {
            adapter = deviceAdapter
            layoutManager = staggeredGridLayoutManager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    override fun addViewListener() {
        binding.apply {
            ivUser.setOnClickListener {
                openActivity(UserInfoActivity::class.java)
            }

            lnChart.setOnClickListener {
                openActivity(ChartActivity::class.java)
            }

            lnMode.setOnClickListener {
                openActivity(SettingActivity::class.java)
            }
        }
    }

    override fun setColorTextStatusBar() {

    }

    /**
     * Double click back to exit app
     */
    private fun existApp() {
        if (isUserCloseApp) {
            finishAndRemoveTask()
        } else {
            showToast(getString(R.string.application_touch_twice_to_exits))
            isUserCloseApp = true
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    isUserCloseApp = false
                }
            }, 3000)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.apply {
            realtimeData.observe(this@MainActivity) { data ->
                binding.apply {
                    tvTemperatureValue.text = String.format(getString(R.string.main_activity_textview_celsius_unit), data.tem)
                    tvPercentHumidity.text = "${getString(R.string.main_activity_textview_percent_humi, data.humi)}%"
                }
            }

            childDevice.observe(this@MainActivity) { data ->
                when (data.second) {
                    "get" -> {
                        deviceAdapter.addData(data.first)
                    }

                    "update" -> {
                        deviceAdapter.updateData(data.first)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        existApp()
    }
}