package com.wkitinerary.domain

import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun addTrip(trip: Trip): Long

    suspend fun getTrips(): Flow<List<Trip>>

    suspend fun getTrip(id: Long?): Flow<Trip>
}