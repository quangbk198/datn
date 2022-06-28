package com.example.datn.di.module

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by quangnh
 * Date: 28/6/2022
 * Time: 11:28 PM
 * Project datn
 */

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

}