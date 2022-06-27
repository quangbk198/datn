package com.example.datn.di.component.apppref

import android.content.Context
import android.content.SharedPreferences
import com.example.datn.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    @ApplicationContext context: Context
) : AppPreference {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun getSharePreferences(): SharedPreferences {
        return sharedPreferences
    }

    override fun saveString(key: String, value: String) {
        getSharePreferences().edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return getSharePreferences().getString(key, defaultValue) ?: defaultValue
    }

    override fun saveBoolean(key: String, value: Boolean) {
        getSharePreferences().edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getSharePreferences().getBoolean(key, defaultValue)
    }

    override fun saveInt(key: String, value: Int) {
        getSharePreferences().edit().putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return getSharePreferences().getInt(key, defaultValue)
    }

    override fun saveFloat(key: String, value: Float) {
        getSharePreferences().edit().putFloat(key, value).apply()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return getSharePreferences().getFloat(key, defaultValue)
    }

    override fun saveLong(key: String, value: Long) {
        getSharePreferences().edit().putLong(key, value).apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return getSharePreferences().getLong(key, defaultValue)
    }

}