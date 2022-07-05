package com.example.datn.features.login.repository

import io.reactivex.Single

interface LoginRepository {

    /**
     * Get password from firebase database
     */
    fun getPass(): Single<Pair<String?, String>>

}