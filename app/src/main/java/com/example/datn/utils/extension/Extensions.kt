package com.example.datn.utils.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun delayFunction(timeDelay: Long, function: () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        delay(timeMillis = timeDelay)
        function.invoke()
    }
}