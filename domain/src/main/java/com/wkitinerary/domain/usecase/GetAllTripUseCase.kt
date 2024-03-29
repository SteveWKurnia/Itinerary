package com.wkitinerary.domain.usecase

import com.wkitinerary.domain.TripRepository
import com.wkitinerary.domain.Trip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTripUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(): Flow<List<Trip>> {
        return tripRepository.getTrips()
    }
}
