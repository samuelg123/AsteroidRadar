package com.udacity.asteroidradar.features.asteroid.data.repository

import com.udacity.asteroidradar.AppDatabase
import com.udacity.asteroidradar.core.datasource.toIso8601String
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.NasaApiService
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.dto.asDatabaseModel
import com.udacity.asteroidradar.features.asteroid.data.entity.asDomainModel
import com.udacity.asteroidradar.features.asteroid.domain.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class AsteroidRepository @Inject constructor(
    private val database: AppDatabase,
    private val api: NasaApiService,
) {

    /**
     * Get asteroids starting from today onwards.
     */
    val asteroids: Flow<List<Asteroid>> =
        database.asteroidDao.getAsteroids(startFrom = today()).map {
            it.asDomainModel()
        }

    private fun today() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0) // Zeroing time. Only take date.
    }.time

    /**
     * Refresh asteroids data between today and the next 7 days
     */
    suspend fun refreshAsteroids(
        startDate: Date, endDate: Date
    ) {
        withContext(Dispatchers.IO) {
            val response =
                api.getNeo(
                    startDate.toIso8601String(),
                    endDate.toIso8601String()
                ).await()
            val asteroids = response.nearEarthObjects.values.flatten()
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }


    /**
     * Remove asteroids before today
     */
    suspend fun removeAsteroidsBeforeToday() =
        database.asteroidDao.removeBeforeToday()

}