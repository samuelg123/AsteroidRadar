package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.features.neo.data.datasource.database.AsteroidDao
import com.udacity.asteroidradar.features.neo.data.entity.AsteroidEntity

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
        }
    }
}