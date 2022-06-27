package com.example.datn.core.base

interface BaseBehavior {
    fun onBecomeVisible()
    fun onBecomeInvisible()
    fun onCommonViewLoaded()
    fun addViewListener()
    fun addDataObserver()
    fun onKeyboardShowing(isShowing : Boolean)
    fun onLoading(isLoading: Boolean = false)
    fun onError(error: Any)
}