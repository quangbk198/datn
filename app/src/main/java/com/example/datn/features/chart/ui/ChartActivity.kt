package com.example.datn.features.chart.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.datn.R
import com.example.datn.core.base.BaseActivity
import com.example.datn.data.model.HumiGraphModel
import com.example.datn.data.model.TemGraphModel
import com.example.datn.databinding.ActivityChartBinding
import com.example.datn.features.chart.viewmodel.ChartViewModel
import com.example.datn.utils.Constants
import com.example.datn.utils.CustomXAxisRenderer
import com.example.datn.utils.DialogView
import com.example.datn.utils.extension.showToast
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Transformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class ChartActivity : BaseActivity<ActivityChartBinding, ChartViewModel>() {

    private var timeCalendar = Constants.TypeCalendar.DATE

    private var labelsNames: ArrayList<String> = arrayListOf()

    private var mNumberXMoveToLast: Int = 0

    private var mNumberYMoveToLast: Int = 0

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

                                viewModel.apply { getTemHumiByDay(dayDayType, monthDayType, yearDayType) }
//                                viewModel.apply { getTemHumiByDay(1, 1, 2022) }
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
            processDataGraphSuccess.observe(this@ChartActivity) { success ->
                if (success) {
                    if (listDataTemByDay.isEmpty()) {
                        showToast(getString(R.string.chart_activity_data_tem_empty))
                    } else if (listDataHumiByDay.isEmpty()) {
                        showToast(getString(R.string.chart_activity_data_humi_empty))
                    } else if (listDataTemByDay.isEmpty() && listDataHumiByDay.isEmpty()) {
                        binding.apply {
                            lcLineChart.visibility = View.INVISIBLE
                            tvNoData.visibility = View.VISIBLE
                        }
                        showToast(getString(R.string.chart_activity_data_chart_empty))
                    } else {
                        binding.apply {
                            lcLineChart.visibility = View.VISIBLE
                            tvNoData.visibility = View.GONE
                        }
                        setDataLineTemChart(dataValueDayLineTem(listDataTemByDay))
                        setDataLineHumiChart(dataValueDayLineHumi(listDataHumiByDay))
                    }
                } else {
                    binding.apply {
                        lcLineChart.visibility = View.INVISIBLE
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setDataLineTemChart(dataTemperature: ArrayList<Entry>) {
        val lineDataTem = LineDataSet(dataTemperature, Constants.EMPTY_STRING)

        viewModel.dataSets.clear()
        viewModel.dataSets.add(lineDataTem)

        lineDataTem.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$value°C"
            }
        }

        lineDataTem.apply {
            lineWidth = 2f
            color = resourcesService.getColor(R.color.red_light)
            setDrawCircles(false)
            setDrawValues(true)
            valueTextSize = 11f
            valueTextColor = getColor(R.color.red_light)
            axisDependency = YAxis.AxisDependency.RIGHT
        }

        setupLineChart(viewModel.dataSets, lineDataTem)
    }

    private fun setDataLineHumiChart(dataHumidity: ArrayList<Entry>) {
        val lineDataHumi = LineDataSet(dataHumidity, Constants.EMPTY_STRING)
        viewModel.dataSets.add(lineDataHumi)

        lineDataHumi.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$value%"
            }
        }

        lineDataHumi.apply {
            lineWidth = 2f
            color = resourcesService.getColor(R.color.light_blue_2)
            setDrawCircles(false)
            valueTextSize = 11f
            valueTextColor = getColor(R.color.light_blue_2)
            axisDependency = YAxis.AxisDependency.RIGHT
        }
        setupLineChart(viewModel.dataSets, lineDataHumi)
    }

    private fun dataValueDayLineTem(listData: List<TemGraphModel>): ArrayList<Entry> {
        val dataVal: ArrayList<Entry> = arrayListOf()

        labelsNames.clear()
        mNumberXMoveToLast = 0
        mNumberYMoveToLast = 0

        for ((index, itemData) in listData.withIndex()) {
            itemData.tem?.let {
                dataVal.add(Entry(index.toFloat(), it.toFloat()))
                mNumberYMoveToLast = it.toInt()
                mNumberXMoveToLast = index

                labelsNames.add(itemData.time.toString())
            }
        }
        return dataVal
    }

    private fun dataValueDayLineHumi(listData: List<HumiGraphModel>): ArrayList<Entry> {
        val dataVal: ArrayList<Entry> = arrayListOf()

        labelsNames.clear()
        mNumberXMoveToLast = 0
        mNumberYMoveToLast = 0

        for ((index, itemData) in listData.withIndex()) {
            itemData.humi?.let {
                dataVal.add(Entry(index.toFloat(), it.toFloat()))
                mNumberYMoveToLast = it.toInt()
                mNumberXMoveToLast = index

                labelsNames.add(itemData.time.toString())
            }
        }
        return dataVal
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
        val yAxisL: YAxis = binding.lcLineChart.axisLeft
        yAxisL.isEnabled = false
        val yAxisR: YAxis = binding.lcLineChart.axisRight
        yAxisR.isEnabled = false

        val xAxis: XAxis = binding.lcLineChart.xAxis
        xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labelsNames)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawAxisLine(true)
            setDrawGridLines(true)
            enableGridDashedLine(10f, 10f, 10f)
            gridColor = ContextCompat.getColor(this@ChartActivity, R.color.dark_blue)
            granularity = 1f
            axisLineWidth = 2f
            axisLineColor = ContextCompat.getColor(this@ChartActivity, R.color.dark_blue)
            setDrawLabels(true)
            textColor = ContextCompat.getColor(this@ChartActivity, R.color.dark_blue)
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
            moveViewTo(mNumberXMoveToLast.toFloat(), mNumberYMoveToLast.toFloat(), null)
        }

        val data = LineData(dataSet)
        binding.lcLineChart.apply {
            setData(data)
            invalidate()
        }
    }
}