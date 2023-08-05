package com.wkitinerary.ui.addtrip

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.AddTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.DATE
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(
    private val useCase: AddTripUseCase
) : ViewModel() {

    fun addTrip(tripTitle: String, tripImage: Int, departureDate: Date, returnDate: Date, onClick: (Long) -> Unit) {
        val dates = getTripDates(departureDate, returnDate)
        viewModelScope.launch {
            val trip = Trip(
                title = tripTitle,
                image = tripImage,
                departureDate = departureDate,
                returnDate = returnDate,
                dates = dates
            )
            val id = useCase(trip)
            onClick(id)
        }
    }

    private fun getTripDates(departureDate: Date, returnDate: Date): List<Date> {
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
