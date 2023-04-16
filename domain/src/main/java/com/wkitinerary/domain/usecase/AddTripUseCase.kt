package com.wkitinerary.domain.usecase

import com.wkitinerary.domain.HomeRepository
import com.wkitinerary.domain.Trip
import javax.inject.Inject

class AddTripUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(trip: Trip) {
        homeRepository.addTrip(trip)
    }
}
