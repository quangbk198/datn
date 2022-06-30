package com.example.datn.features.userinfo.repository

import android.util.Log
import com.example.datn.R
import com.example.datn.data.remote.FirebaseDatabaseService
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : UserInfoRepository {

    override fun getPass(): Single<Pair<String?, String>> {
        return firebaseDatabaseService.getPass()
    }

    override fun updatePass(newPass: String): Single<String> {
        return firebaseDatabaseService.updatePass(newPass)
    }
}