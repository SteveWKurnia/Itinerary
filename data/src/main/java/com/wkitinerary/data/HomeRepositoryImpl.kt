package com.wkitinerary.data

import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.data.database.entity.DateEntity
import com.wkitinerary.data.database.entity.TripEntity
import com.wkitinerary.domain.HomeRepository
import com.wkitinerary.domain.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val tripDao: TripDao
) : HomeRepository {

    override suspend fun addTrip(trip: Trip) {
        val id = tripDao.addTrip(
            TripEntity(
                title = trip.title,
                image = trip.image,
                returnDate = trip.returnDate,
                departureDate = trip.departureDate
            )
        )
        trip.dates.forEach {
            tripDao.addDate(DateEntity(tripId = id, date = it, activity = "Active!"))
        }
    }

    override suspend fun getTrips(): Flow<List<Trip>> {
        return tripDao.getAllTrip().map {
            it.map { trip ->
                Trip(trip.title, trip.image, trip.departureDate, trip.returnDate, listOf())
            }
        }
    }
}
