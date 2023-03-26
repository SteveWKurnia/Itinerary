package com.wkitinerary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wkitinerary.data.database.dao.TripDao
import com.wkitinerary.data.database.entity.TripEntity

@Database(
    version = 1,
    entities = [TripEntity::class],
    exportSchema = false
)
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