package com.example.datn.data.remote

import android.content.Context
import android.util.Log
import com.example.datn.R
import com.example.datn.data.model.*
import com.example.datn.utils.Constants
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

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

    /**
     * Get temperature and humidity realtime
     */
    fun getRealtimeDataTemAndHumi(): Observable<TemHumiWrapModel> {
        val myRef = firebaseDatabase.getReference("swiftlet_home/user/admin/device/sht_sensor/real_time")

        return Observable.create { emitter ->
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(TemHumiWrapModel::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    /**
     * Get list child device
     */
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

    /**
     * Get list temperature and humidity by day
     */
    fun getTemHumiByDay(
        day: Int,
        month: Int,
        year: Int
    ): Observable<Triple<TemHumiWrapModel, String, Boolean>> {

        return Observable.create { emitter ->
            val path = "swiftlet_home/user/admin/device/sht_sensor/chart_value/year/$year/month/$month/day/$day/hour"

            val myRef = firebaseDatabase.getReference(path)

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshotHour: DataSnapshot) {
                    if (snapshotHour.value == null) {
                        emitter.onNext(Triple(TemHumiWrapModel(), context.getString(R.string.chart_activity_data_chart_empty), false))
                    } else {
                        var sizeSnapshotHour = 0
                        snapshotHour.children.forEach { _ ->
                            sizeSnapshotHour++
                        }

                        snapshotHour.children.forEachIndexed { indexHour, dataSnapshot ->
                            dataSnapshot.key?.let { keyHour ->

                                firebaseDatabase.getReference("$path/$keyHour").addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.value == null) {
                                            emitter.onNext(Triple(TemHumiWrapModel(), context.getString(R.string.chart_activity_data_chart_empty), false))
                                        } else {
                                            var sizeSnapshot = 0
                                            snapshot.children.forEach { _ ->
                                                sizeSnapshot++
                                            }

                                            snapshot.children.forEachIndexed { indexHourDetail, data ->
                                                data.key?.let { keyDetail ->
                                                    val temHumiModel = snapshot.child(keyDetail).getValue(TemHumiWrapModel::class.java)
                                                    if (temHumiModel != null) {
                                                        if ((indexHour == sizeSnapshotHour - 1) && (indexHourDetail == sizeSnapshot - 1)) {
                                                            emitter.onNext(Triple(temHumiModel, Constants.EMPTY_STRING, true))
                                                        } else {
                                                            emitter.onNext(Triple(temHumiModel, Constants.EMPTY_STRING, false))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        emitter.onNext(Triple(TemHumiWrapModel(), error.message, false))
                                    }
                                })
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onNext(Triple(TemHumiWrapModel(), error.message, false))
                }
            })
        }
    }

    /**
     * Set threshold
     */
    fun setThreshold(
        thresholdModel: ThresholdModel,
        ref: DatabaseReference
    ) : Single<Int> {
        return Single.create { emitter ->
            ref.setValue(thresholdModel) { error, _ ->
                if (error != null) {
                    emitter.onSuccess(-1)
                } else {
                    emitter.onSuccess(1)
                }
            }
        }
    }

    /**
     * Set output condition
     */
    fun setOutputCondition(
        outputCondition: OutputConditionModel,
        ref: DatabaseReference
    ) : Single<Int> {
        return Single.create { emitter ->
            ref.setValue(outputCondition) { error, _ ->
                if (error != null) {
                    emitter.onSuccess(-1)
                } else {
                    emitter.onSuccess(1)
                }
            }
        }
    }

    /**
     * Get temperature threshold
     */
    fun getTemperatureThreshold(): Observable<ThresholdResponse> {
        return Observable.create { emitter ->
            val path = "swiftlet_home/user/admin/device/sht_sensor/threshold/input/tem"

            val myRef = firebaseDatabase.getReference(path)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(ThresholdResponse::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    /**
     * Get humidity threshold
     */
    fun getHumidityThreshold(): Observable<ThresholdResponse> {
        return Observable.create { emitter ->
            val path = "swiftlet_home/user/admin/device/sht_sensor/threshold/input/humi"

            val myRef = firebaseDatabase.getReference(path)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(ThresholdResponse::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    /**
     * Get state light device
     */
    fun getStateLightDevice(): Observable<OutputConditionResponse> {
        return Observable.create {  emitter ->
            val path = "swiftlet_home/user/admin/device/sht_sensor/threshold/output/light"

            val myRef = firebaseDatabase.getReference(path)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(OutputConditionResponse::class.java)
                    if (data != null) {
                        emitter.onNext(data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    /**
     * Get state pump device
     */
    fun getStatePumpDevice(): Observable<OutputConditionResponse> {
        return Observable.create {  emitter ->
            val path = "swiftlet_home/user/admin/device/sht_sensor/threshold/output/pump"

            val myRef = firebaseDatabase.getReference(path)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(OutputConditionResponse::class.java)
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