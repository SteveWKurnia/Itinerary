package com.wkitinerary.data.database.di

import android.content.Context
import com.wkitinerary.data.database.ItineraryDatabase
import com.wkitinerary.data.database.dao.TripDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): ItineraryDatabase {
        return ItineraryDatabase.getDatabase(context)
    }

    @Provides
    fun providesTripDao(
        database: ItineraryDatabase
    ): TripDao {
        return database.tripDao()
    }
}
