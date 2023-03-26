package com.wkitinerary.domain.usecase

import com.wkitinerary.domain.HomeRepository
import com.wkitinerary.domain.Trip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTripUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<List<Trip>> {
        return homeRepository.getTrips()
    }
}
