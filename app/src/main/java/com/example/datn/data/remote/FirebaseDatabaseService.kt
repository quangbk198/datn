package com.example.datn.data.remote

import android.content.Context
import android.util.Log
import com.example.datn.R
import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.data.model.DataRealtimeTemHumi
import com.example.datn.utils.Constants
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by quangnh
 * Date: 30/6/2022
 * Time: 11:25 PM
 * Project datn
 */
class FirebaseDatabaseService @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val context: Context
) {

    fun getPass(): Single<Pair<String?, String>> {
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

    fun updatePass(newPass: String): Single<String> {
        return Single.create { emitter ->
            val userRef = firebaseDatabase.getReference("swiftlet_home/user/admin/password")

            userRef.setValue(newPass) { error, _ ->
                if (error != null) {
                    emitter.onSuccess(error.message)
                } else {
                    emitter.onSuccess(context.getString(R.string.change_pass_fragment_update_pass_success))
                }
            }
        }
    }

    fun getRealtimeDataTemAndHumi(): Observable<DataRealtimeTemHumi> {
        val myRef = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/real_time")

        return Observable.create { emitter ->
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(DataRealtimeTemHumi::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun getListChildDevice(): Observable<Triple<ChildDeviceModel, String, String>> {
        val myRef = firebaseDatabase.getReference("swiftlet_home/user/admin/child_device")

        return Observable.create { emitter ->
            myRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val device = snapshot.getValue(ChildDeviceModel::class.java)
                    if (device != null) {
                        emitter.onNext(Triple(device, "get", Constants.EMPTY_STRING))
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val device = snapshot.getValue(ChildDeviceModel::class.java)
                    if (device != null) {
                        emitter.onNext(Triple(device, "update", Constants.EMPTY_STRING))
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onNext(Triple(ChildDeviceModel(), "error", error.message))
                }
            })
        }
    }
}