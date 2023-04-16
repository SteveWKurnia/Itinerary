package com.wkitinerary.ui.home

sealed interface HomeItems {
    data class Trip(
        val title: String,
        val image: Int,
        val departureDate: String,
        val returnDate: String
    ) : HomeItems

    object AddTrip : HomeItems
}