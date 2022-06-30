package com.example.datn.di.module

import com.example.datn.features.login.repository.LoginRepository
import com.example.datn.features.login.repository.LoginRepositoryImpl
import com.example.datn.features.userinfo.repository.UserInfoRepository
import com.example.datn.features.userinfo.repository.UserInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by quangnh
 * Date: 28/6/2022
 * Time: 11:33 PM
 * Project datn
 */

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    fun provideUserInfoRepository(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository

}