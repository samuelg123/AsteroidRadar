package com.udacity.asteroidradar.features.neo.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.features.neo.data.entity.AsteroidEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
    fun getAsteroids(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate >= :startFrom ORDER BY closeApproachDate ASC")
    fun getAsteroids(startFrom: Date): Flow<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("DELETE FROM asteroid WHERE closeApproachDate < DATE('now')")
    suspend fun removeBeforeToday()
}