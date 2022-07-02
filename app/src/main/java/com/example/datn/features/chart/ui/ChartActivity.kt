package com.example.datn.features.chart.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityChartBinding
import com.example.datn.features.chart.viewmodel.ChartViewModel
import com.example.datn.utils.Constants
import com.example.datn.utils.DialogView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class ChartActivity : BaseActivity<ActivityChartBinding, ChartViewModel>() {

    private var timeCalendar = Constants.TypeCalendar.DATE

    override val viewModel: ChartViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityChartBinding =
        ActivityChartBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {
        setTextViewTime(timeCalendar)
        setTypeCalendar(timeCalendar)
    }

    override fun addViewListener() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }

            ivCalendar.setOnClickListener {
                when (timeCalendar) {
                    Constants.TypeCalendar.DATE -> {
                        DialogView.showDatePickerDialog(this@ChartActivity) { date ->
                            viewModel.apply {
                                dayDayType = date.first
                                monthDayType = date.second
                                yearDayType = date.third
                                setTextViewTime(timeCalendar)
                            }
                        }
                    }

                    Constants.TypeCalendar.MONTH -> {
                        DialogView.showMonthPickerDialog(this@ChartActivity) { date ->
                            viewModel.apply {
                                monthMonthType = date.first
                                yearMonthType = date.second
                                setTextViewTime(timeCalendar)
                            }
                        }
                    }
                }
            }

            tvDay.setOnClickListener {
                timeCalendar = Constants.TypeCalendar.DATE
                setTypeCalendar(timeCalendar)
                setTextViewTime(timeCalendar)
            }

            tvMonth.setOnClickListener {
                timeCalendar = Constants.TypeCalendar.MONTH
                setTypeCalendar(timeCalendar)
                setTextViewTime(timeCalendar)
            }
        }
    }

    private fun setTextViewTime(timeCalendar: Constants.TypeCalendar) {
        binding.apply {
            viewModel.apply {
                when (timeCalendar) {
                    Constants.TypeCalendar.DATE -> {
                        tvLabelDate.text = getString(R.string.chart_activity_textview_day_data)
                        tvDate.text = "$dayDayType/$monthDayType/$yearDayType"
                    }

                    Constants.TypeCalendar.MONTH -> {
                        tvLabelDate.text = getString(R.string.chart_activity_textview_month_data)
                        tvDate.text = "$monthMonthType/$yearMonthType"
                    }
                }
            }
        }
    }

    private fun setTypeCalendar(timeCalendar: Constants.TypeCalendar) {
        binding.apply {
            when (timeCalendar) {
                Constants.TypeCalendar.DATE -> {
                    tvDay.isSelected = true
                    tvMonth.isSelected = false

                    tvDay.setTextColor(
                        ContextCompat.getColor(
                            this@ChartActivity,
                            R.color.white
                        )
                    )

                    tvMonth.setTextColor(
                        ContextCompat.getColor(
                            this@ChartActivity,
                            R.color.dark_blue
                        )
                    )
                }

                Constants.TypeCalendar.MONTH -> {
                    tvDay.isSelected = false
                    tvMonth.isSelected = true

                    tvDay.setTextColor(
                        ContextCompat.getColor(
                            this@ChartActivity,
                            R.color.dark_blue
                        )
                    )

                    tvMonth.setTextColor(
                        ContextCompat.getColor(
                            this@ChartActivity,
                            R.color.white
                        )
                    )
                }
            }
        }
    }
}