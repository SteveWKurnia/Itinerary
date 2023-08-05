package com.wkitinerary.domain.usecase

import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.TripRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTripUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(id: Long?): Flow<Trip> {
        return tripRepository.getTrip(id)
    }
}