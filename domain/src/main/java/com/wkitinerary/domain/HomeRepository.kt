package com.wkitinerary.domain

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun addTrip(trip: Trip)

    suspend fun getTrips(): Flow<List<Trip>>
}