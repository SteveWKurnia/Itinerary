package com.wkitinerary.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.wkitinerary.data.database.entity.DateEntity
import com.wkitinerary.data.database.entity.TripEntity

data class TripRelation(
    @Embedded
    val trip: TripEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val dates: List<DateEntity>
)
