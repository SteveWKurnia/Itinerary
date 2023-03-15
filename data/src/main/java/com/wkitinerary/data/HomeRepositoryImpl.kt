package com.wkitinerary.data

import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.data.database.entity.TripEntity
import com.wkitinerary.domain.HomeRepository
import com.wkitinerary.domain.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl(
    private val tripDao: TripDao
): HomeRepository {

    override suspend fun addTrip(trip: Trip) {
        tripDao.addTrip(TripEntity(title = trip.title))
    }

    override suspend fun getTrips(): Flow<List<Trip>> {
        return tripDao.getAllTrip().map {
            it.map { trip ->
                Trip(trip.title)
            }
        }
    }

}