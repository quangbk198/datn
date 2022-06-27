package com.example.datn.utils.extension

import android.content.Context
import android.widget.Toast
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

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}