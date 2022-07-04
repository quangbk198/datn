package com.example.datn.features.chart.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivityChartBinding
import com.example.datn.features.chart.viewmodel.ChartViewModel
import com.example.datn.utils.Constants
import com.example.datn.utils.CustomXAxisRenderer
import com.example.datn.utils.DialogView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Transformer
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

                                //viewModel.apply { getTemHumiByDay(dayDayType, monthDayType, yearDayType) }
                                viewModel.apply { getTemHumiByDay(1, 1, 2022) }
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

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.apply {

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

    private fun setupLineChart(dataSet: ArrayList<ILineDataSet>, lineData: LineDataSet) {
        binding.apply {
            val yAxisL = lcLineChart.axisLeft
            yAxisL.isEnabled = false

            val yAxisR = lcLineChart.axisRight
            yAxisR.isEnabled = false

            val xAxis = lcLineChart.xAxis

            xAxis.apply {
                textColor = Color.BLACK
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(true)
                setDrawGridLines(true)
                enableGridDashedLine(10f, 10f, 10f)
                gridColor = Color.WHITE
                granularity = 1f
                axisLineWidth = 2f
                axisLineColor = Color.WHITE
                axisMaximum = lineData.xMax + 0.25f
                isGranularityEnabled = true
            }

            //set two text line in XAxis
            val trans: Transformer = binding.lcLineChart.getTransformer(YAxis.AxisDependency.LEFT)
            binding.lcLineChart.apply {
                setXAxisRenderer(CustomXAxisRenderer(this.viewPortHandler, this.xAxis, trans))
                setPinchZoom(false)
                extraBottomOffset = 20f
                extraRightOffset = 30f
                extraLeftOffset = 10f
                setDrawBorders(false)
                legend.isEnabled = false
                description.isEnabled = false
                isDragEnabled = true
                isScaleYEnabled = false
                setVisibleXRangeMaximum(7f)
                setVisibleXRangeMinimum(6f)
                setMaxVisibleValueCount(50)
                //moveViewTo(mNumberXMoveToLast.toFloat(), mNumberYMoveToLast.toFloat(), null)
            }

            val data = LineData(dataSet)
            lcLineChart.apply {
                setData(data)
                invalidate()
            }
        }
    }
}