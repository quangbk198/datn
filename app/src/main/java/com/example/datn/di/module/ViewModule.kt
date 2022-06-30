package com.example.datn.di.module

import android.content.Context
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.datn.features.main.ui.adapter.DeviceAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by quangnh
 * Date: 1/7/2022
 * Time: 12:00 AM
 * Project datn
 */

@InstallIn(ActivityComponent::class)
@Module
class ViewModule {

    @Provides
    fun provideStaggeredGridLayoutManager(): StaggeredGridLayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    @Provides
    fun provideDeviceAdapter(
        @ApplicationContext context: Context
    ): DeviceAdapter {
        return DeviceAdapter(context)
    }
}