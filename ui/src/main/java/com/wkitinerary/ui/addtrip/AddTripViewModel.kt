package com.wkitinerary.ui.addtrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.AddTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(
    private val useCase: AddTripUseCase
) : ViewModel() {
    fun addTrip(tripTitle: String, tripImage: Int, departureDate: String, returnDate: String) {
        viewModelScope.launch {
            val trip = Trip(tripTitle, tripImage, departureDate, returnDate)
            useCase(trip)
        }
    }
}
