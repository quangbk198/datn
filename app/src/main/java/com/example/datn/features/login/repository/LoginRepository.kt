package com.example.datn.features.login.repository

import io.reactivex.Single

/**
 * Created by quangnh
 * Date: 28/6/2022
 * Time: 11:32 PM
 * Project datn
 */
interface LoginRepository {

    /**
     * Get password from firebase database
     */
    fun getPass(): Single<Pair<String?, String>>

}