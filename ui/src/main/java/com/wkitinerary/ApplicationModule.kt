package com.wkitinerary

import com.wkitinerary.utils.CustomDispatcher
import com.wkitinerary.utils.CustomDispatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideDispatchers(): CustomDispatcher = CustomDispatcherImpl()
}