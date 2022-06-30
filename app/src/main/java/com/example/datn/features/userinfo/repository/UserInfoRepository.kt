package com.example.datn.features.userinfo.repository

import io.reactivex.Single

interface UserInfoRepository {
    /**
     * Get password from firebase database
     */
    fun getPass(): Single<Pair<String?, String>>

    /**
     * Update password
     */
    fun updatePass(newPass: String): Single<String>
}