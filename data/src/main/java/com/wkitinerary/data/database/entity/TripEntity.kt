package com.wkitinerary.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "trip")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image")
    val image: Int,
    @ColumnInfo(name = "departure_date")
    val departureDate: Date,
    @ColumnInfo(name = "return_date")
    val returnDate: Date,
)
