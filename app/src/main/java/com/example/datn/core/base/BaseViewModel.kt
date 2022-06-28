package com.example.datn.core.base

import androidx.lifecycle.ViewModel
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.di.component.scheduler.SchedulerProvider
import com.example.datn.di.component.sharepref.SharedPref
import com.example.datn.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel : ViewModel() {
    lateinit var compositeDisposable: CompositeDisposable

    lateinit var schedulerProvider: SchedulerProvider

    lateinit var resourcesService: ResourcesService

    lateinit var dataManager: SharedPref

    open var isInitialized: Boolean = false

    open val loadingState = SingleLiveEvent<Boolean>()

    open val errorState = SingleLiveEvent<String>()


    internal fun init(
        schedulerProvider: SchedulerProvider,
        resourcesService: ResourcesService,
        dataManager: SharedPref
    ) {
        this.schedulerProvider = schedulerProvider
        this.resourcesService = resourcesService
        this.dataManager = dataManager
        compositeDisposable = CompositeDisposable()
        isInitialized = true
    }

    fun setLoading(isLoading: Boolean) {
        loadingState.value = isLoading
    }

    fun setErrorString(errorMessage: String) {
        errorState.value = errorMessage
    }

    abstract fun onDidBindViewModel()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}