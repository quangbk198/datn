package com.example.datn.features.login.repository

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
    private val firebaseDatabase: FirebaseDatabase
) : LoginRepository {

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
}