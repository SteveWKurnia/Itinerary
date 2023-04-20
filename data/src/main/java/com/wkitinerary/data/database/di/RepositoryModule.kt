package com.wkitinerary.data.database.di

import com.wkitinerary.data.HomeRepositoryImpl
import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    fun providesHomeRepository(tripDao: TripDao): HomeRepository {
        return HomeRepositoryImpl(tripDao)
    }

}
