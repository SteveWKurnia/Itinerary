package com.wkitinerary.domain

import java.util.Date

data class Trip(
    val id: Long? = 0L,
    val title: String,
    val image: Int,
    val departureDate: Date,
    val returnDate: Date,
    val dates: List<Date>
)
