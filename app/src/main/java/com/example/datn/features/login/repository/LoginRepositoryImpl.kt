package com.example.datn.features.login.repository

import com.example.datn.data.remote.FirebaseDatabaseService
import com.example.datn.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by quangnh
 * Date: 28/6/2022
 * Time: 11:32 PM
 * Project datn
 */
class LoginRepositoryImpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService,
) : LoginRepository {

    override fun getPass(): Single<Pair<String?, String>> {
        return firebaseDatabaseService.getPass()
    }
}