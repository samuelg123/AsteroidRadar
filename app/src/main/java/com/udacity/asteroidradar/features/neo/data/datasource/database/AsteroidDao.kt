package com.udacity.asteroidradar.features.neo.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.features.neo.data.entity.AsteroidEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("select * from asteroid")
    fun getAsteroids(): Flow<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidEntity)
}