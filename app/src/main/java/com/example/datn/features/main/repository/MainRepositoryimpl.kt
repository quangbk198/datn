package com.example.datn.features.main.repository

import com.example.datn.data.model.DataRealtime
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import javax.inject.Inject

class MainRepositoryimpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : MainRepository {

    override fun getRealtimeDataTemAndHumi(): Observable<DataRealtime> {
        val myRef = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/real_time")

        return Observable.create { emitter ->
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(DataRealtime::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}