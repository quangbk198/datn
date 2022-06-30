package com.example.datn.features.userinfo.repository

import android.util.Log
import com.example.datn.R
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val resourcesService: ResourcesService
) : UserInfoRepository {

    override fun getPass(): Single<Pair<String?, String>> {
        return Single.create { emitter ->
            val userRef = firebaseDatabase.getReference("swiftlet_home/user/admin/password")

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    emitter.onSuccess(Pair(snapshot.getValue(String::class.java), Constants.EMPTY_STRING))
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onSuccess(Pair(Constants.EMPTY_STRING, error.message))
                }
            })
        }
    }

    override fun updatePass(newPass: String): Single<String> {
        return Single.create { emitter ->
            val userRef = firebaseDatabase.getReference("swiftlet_home/user/admin/password")

            userRef.setValue(newPass) { error, _ ->
                if (error != null) {
                    emitter.onSuccess(error.message)
                } else {
                    emitter.onSuccess(resourcesService.getString(R.string.change_pass_fragment_update_pass_success))
                }
            }
        }
    }
}