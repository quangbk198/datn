package com.example.datn.di.component.sharepref

import android.content.SharedPreferences
import com.example.datn.di.component.apppref.AppPreference
import com.example.datn.utils.Constants
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(
    private val mPreferences: AppPreference
) : SharedPref{
    override fun getSharePreferences(): SharedPreferences {
        return mPreferences.getSharePreferences()
    }

    override fun setStateLogin(state: Boolean) {
        getSharePreferences().edit().putBoolean(Constants.SharePrefKey.StateLogin.key, state).apply()
    }

    override fun getStateLogin(): Boolean {
        return getSharePreferences().getBoolean(Constants.SharePrefKey.StateLogin.key, false)
    }
}