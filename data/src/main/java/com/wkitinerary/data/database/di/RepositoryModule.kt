package com.wkitinerary.data.database.di

import com.wkitinerary.data.TripRepositoryImpl
import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.domain.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    fun providesHomeRepository(tripDao: TripDao): TripRepository {
        return TripRepositoryImpl(tripDao)
    }

}
