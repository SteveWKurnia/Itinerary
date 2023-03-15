package com.wkitinerary.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wkitinerary.data.database.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Insert
    suspend fun addTrip(tripEntity: TripEntity)

    @Query("SELECT * FROM trip")
    fun getAllTrip(): Flow<List<TripEntity>>
}