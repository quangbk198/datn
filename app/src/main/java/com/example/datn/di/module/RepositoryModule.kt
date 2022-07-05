package com.example.datn.di.module

import com.example.datn.features.chart.repository.ChartRepository
import com.example.datn.features.chart.repository.ChartRepositoryImpl
import com.example.datn.features.login.repository.LoginRepository
import com.example.datn.features.login.repository.LoginRepositoryImpl
import com.example.datn.features.main.repository.MainRepository
import com.example.datn.features.main.repository.MainRepositoryimpl
import com.example.datn.features.setting.repository.SettingRepository
import com.example.datn.features.setting.repository.SettingRepositoryImpl
import com.example.datn.features.userinfo.repository.UserInfoRepository
import com.example.datn.features.userinfo.repository.UserInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    fun provideUserInfoRepository(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository

    @Binds
    fun provideUserMainRepository(mainRepositoryimpl: MainRepositoryimpl): MainRepository

    @Binds
    fun provideUserChartRepository(chartRepositoryImpl: ChartRepositoryImpl): ChartRepository

    @Binds
    fun provideUserSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository
}