package com.wkitinerary.domain.usecase

import com.wkitinerary.domain.TripRepository
import com.wkitinerary.domain.Trip
import javax.inject.Inject

class AddTripUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(trip: Trip): Long {
        return tripRepository.addTrip(trip)
    }
}
