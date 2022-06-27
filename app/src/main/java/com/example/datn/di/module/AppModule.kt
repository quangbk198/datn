package com.example.datn.di.module

import com.example.datn.di.component.apppref.AppPreference
import com.example.datn.di.component.apppref.AppPreferenceImpl
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.di.component.resource.ResourcesServiceImpl
import com.example.datn.di.component.scheduler.SchedulerProvider
import com.example.datn.di.component.scheduler.SchedulerProviderImpl
import com.example.datn.di.component.sharepref.SharedPref
import com.example.datn.di.component.sharepref.SharedPrefImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providerAppPreference(appPreferenceImpl: AppPreferenceImpl): AppPreference {
        return appPreferenceImpl
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider {
        return schedulerProviderImpl
    }

    @Provides
    @Singleton
    fun providerResourceServices(resourcesServiceImpl: ResourcesServiceImpl): ResourcesService {
        return resourcesServiceImpl
    }

    @Provides
    @Singleton
    fun providerSharedPref(sharePrefImpl: SharedPrefImpl): SharedPref {
        return sharePrefImpl
    }
}