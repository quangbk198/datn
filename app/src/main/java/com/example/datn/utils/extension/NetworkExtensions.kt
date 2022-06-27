package com.example.datn.utils.extension

import com.example.datn.di.component.scheduler.SchedulerProvider
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NetworkExtensions @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider
) {
    fun checkInternet(function: (Boolean) -> Unit) {
        compositeDisposable.add(
            ReactiveNetwork.checkInternetConnectivity()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { isConnect ->
                    function.invoke(isConnect)
                }
        )
    }
}