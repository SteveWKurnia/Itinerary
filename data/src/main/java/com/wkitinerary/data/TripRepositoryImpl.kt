package com.wkitinerary.data

import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.data.database.entity.DateEntity
import com.wkitinerary.data.database.entity.TripEntity
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TripRepositoryImpl(
    private val tripDao: TripDao
) : TripRepository {

    override suspend fun addTrip(trip: Trip): Long {
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

        return id
    }

    override suspend fun getTrips(): Flow<List<Trip>> {
        return tripDao.getAllTrip().map { tripEntity ->
            tripEntity.map { trip ->
                Trip(
                    trip.id,
                    trip.title,
                    trip.image,
                    trip.departureDate,
                    trip.returnDate,
                    tripDao.getTripDates(trip.id).map { it.date })
            }
        }
    }

    override suspend fun getTrip(id: Long?): Flow<Trip> {
        val dates = tripDao.getTripDates(id!!).map {
            it.date
        }
        return tripDao.getTrip(id).map { trip ->
            Trip(
                id = trip.id,
                title = trip.title,
                image = trip.image,
                departureDate = trip.departureDate,
                returnDate = trip.returnDate,
                dates = dates
            )
        }
    }
}
