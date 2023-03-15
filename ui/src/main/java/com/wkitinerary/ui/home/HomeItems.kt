package com.wkitinerary.ui.home

sealed interface HomeItems {
    data class Trip(val title: String, val image: Int): HomeItems
    object AddTrip: HomeItems
}