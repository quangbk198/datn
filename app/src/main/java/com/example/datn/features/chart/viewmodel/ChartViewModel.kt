package com.example.datn.features.chart.viewmodel

import com.example.datn.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(

) : BaseViewModel() {

    // Dữ liệu ngày, tháng, năm của loại đồ thị theo ngày
    var yearDayType = Calendar.getInstance().get(Calendar.YEAR)
    var monthDayType = Calendar.getInstance().get(Calendar.MONTH) + 1
    var dayDayType = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    // Dữ liệu tháng, năm của loại đồ thị theo tháng
    var yearMonthType = Calendar.getInstance().get(Calendar.YEAR)
    var monthMonthType = Calendar.getInstance().get(Calendar.MONTH) + 1

    override fun onDidBindViewModel() {

    }
}