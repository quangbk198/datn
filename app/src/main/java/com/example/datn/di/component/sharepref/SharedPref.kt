package com.example.datn.di.component.sharepref

import android.content.SharedPreferences

interface SharedPref {
    fun getSharePreferences(): SharedPreferences
    fun setStateLogin(state: Boolean)
    fun getStateLogin(): Boolean
}