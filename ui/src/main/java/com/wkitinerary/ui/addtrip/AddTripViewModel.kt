package com.wkitinerary.ui.addtrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.AddTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.DATE
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(
    private val useCase: AddTripUseCase
) : ViewModel() {
    fun addTrip(tripTitle: String, tripImage: Int, departureDate: Date, returnDate: Date) {
        val dates = getDates(departureDate, returnDate)
        viewModelScope.launch {
            val trip = Trip(tripTitle, tripImage, departureDate, returnDate, dates)
            useCase(trip)
        }
    }

    private fun getDates(departureDate: Date, returnDate: Date): List<Date> {
        val dates = mutableListOf<Date>()

        val departureCalendar = Calendar.getInstance()
        departureCalendar.time = departureDate

        val returnCalendar = Calendar.getInstance()
        returnCalendar.time = returnDate

        while (!departureCalendar.after(returnCalendar)) {
            dates.add(departureCalendar.time)
            departureCalendar.add(DATE, 1)
        }

        return dates
    }
}
