package com.example.datn.features.chart.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datn.R
import com.example.datn.core.base.BaseViewModel
import com.example.datn.data.model.HumiGraphModel
import com.example.datn.data.model.TemGraphModel
import com.example.datn.data.model.TemHumiWrapModel
import com.example.datn.features.chart.repository.ChartRepository
import com.example.datn.utils.Constants
import com.example.datn.utils.extension.RxNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val rxNetwork: RxNetwork
) : BaseViewModel() {

    // Dữ liệu ngày, tháng, năm của loại đồ thị theo ngày
    var yearDayType = Calendar.getInstance().get(Calendar.YEAR)
    var monthDayType = Calendar.getInstance().get(Calendar.MONTH) + 1
    var dayDayType = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    // Dữ liệu tháng, năm của loại đồ thị theo tháng
    var yearMonthType = Calendar.getInstance().get(Calendar.YEAR)
    var monthMonthType = Calendar.getInstance().get(Calendar.MONTH) + 1

    private val listDataGraph: MutableList<TemHumiWrapModel> = mutableListOf()

    private val _listDataTemByDay: MutableLiveData<List<TemGraphModel>> by lazy { MutableLiveData() }

    private val _listDataHumiByDay: MutableLiveData<List<HumiGraphModel>> by lazy { MutableLiveData() }

    val listDataTemByDay: LiveData<List<TemGraphModel>> get() = _listDataTemByDay

    val listDataHumiByDay: LiveData<List<HumiGraphModel>> get() = _listDataHumiByDay

    override fun onDidBindViewModel() {
        // Lấy dữ liệu đồ thị theo ngày
//        getTemHumiByDay(dayDayType, monthDayType, yearDayType)
        getTemHumiByDay(1, 1, 2022)
    }

    /**
     * Lấy dữ liệu đồ thị theo ngày
     */
    fun getTemHumiByDay(
        day: Int,
        month: Int,
        year: Int
    ) {
        rxNetwork.checkInternet { isConnected ->
            if (isConnected) {
                setLoading(true)

                if (listDataGraph.isNotEmpty()) listDataGraph.clear()

                compositeDisposable.add(
                    chartRepository.getTemHumiByDay(day, month, year)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { dataResponse ->
                            if (dataResponse.second != Constants.EMPTY_STRING) {
                                setErrorString(dataResponse.second)
                            } else {
                                if (dataResponse.third) {
                                    // Đã lấy được tất cả dữ liệu trên firebase

                                    listDataGraph.add(dataResponse.first)
                                    mapData(listDataGraph)
                                } else {
                                    listDataGraph.add(dataResponse.first)
                                }
                            }
                        }
                )
            } else {
                setErrorString(resourcesService.getString(R.string.have_no_internet))
            }
        }
    }

    private fun mapDataToListTem(data: List<TemHumiWrapModel>) : Single<List<TemGraphModel>> {
        return Single.create { emitter ->
            val listTem = mutableListOf<TemGraphModel>()

            val grouped = mutableMapOf<Int, MutableList<TemGraphModel>>()

            data.forEach { temHumi ->
                temHumi.time?.let { key ->
                    if (!grouped.containsKey(key)) {
                        grouped[key] = mutableListOf()
                    }
                    grouped[key]?.add(TemGraphModel(temHumi.time, temHumi.tem))
                }
            }

            data.forEach { temHumiModel ->
                listTem.add(TemGraphModel(temHumiModel.time, temHumiModel.tem))
            }
            emitter.onSuccess(listTem)
        }
    }

    private fun mapDataToListHumi(data: List<TemHumiWrapModel>) : Single<List<HumiGraphModel>> {
        return Single.create { emitter ->
            val listHumi = mutableListOf<HumiGraphModel>()

            data.forEach { temHumiModel ->
                listHumi.add(HumiGraphModel(temHumiModel.time, temHumiModel.humi))
            }
            emitter.onSuccess(listHumi)
        }
    }

    /**
     * Tách data thành danh sách nhiệt độ, độ ẩm
     */
    private fun mapData(data: List<TemHumiWrapModel>) {
        compositeDisposable.add(
            Single.zip(
                mapDataToListTem(data),
                mapDataToListHumi(data)
            ) { listTem, listHumi ->
                return@zip Pair(listTem, listHumi)
            }.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { value ->
                    _listDataTemByDay.value = value.first
                    _listDataHumiByDay.value = value.second
                }
        )
    }
}