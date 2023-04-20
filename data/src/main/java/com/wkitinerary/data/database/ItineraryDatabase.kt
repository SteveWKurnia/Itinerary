package com.wkitinerary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wkitinerary.data.database.converters.DateConverter
import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.data.database.entity.DateEntity
import com.wkitinerary.data.database.entity.TripEntity

@Database(
    version = 1,
    entities = [TripEntity::class, DateEntity::class],
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ItineraryDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {

        @Volatile
        private var INSTANCE: ItineraryDatabase? = null

        fun getDatabase(context: Context): ItineraryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItineraryDatabase::class.java,
                    "itinerary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}
