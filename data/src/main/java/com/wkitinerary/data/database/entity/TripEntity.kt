package com.wkitinerary.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image")
    val image: Int,
    @ColumnInfo(name = "departure_date")
    val departureDate: String,
    @ColumnInfo(name = "return_date")
    val returnDate: String,
)