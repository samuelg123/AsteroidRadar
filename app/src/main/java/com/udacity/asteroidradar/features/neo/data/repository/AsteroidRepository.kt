package com.udacity.asteroidradar.features.neo.data.repository

import com.udacity.asteroidradar.AppDatabase
import com.udacity.asteroidradar.features.neo.data.datasource.api.CustomDate
import com.udacity.asteroidradar.features.neo.data.datasource.api.NasaApiService
import com.udacity.asteroidradar.features.neo.data.datasource.api.SIMPLE_FORMAT
import com.udacity.asteroidradar.features.neo.data.datasource.api.dto.asDatabaseModel
import com.udacity.asteroidradar.features.neo.data.datasource.api.formatted
import com.udacity.asteroidradar.features.neo.data.entity.asDomainModel
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class AsteroidRepository @Inject constructor(
   val database: AppDatabase,
   val api: NasaApiService,
) {

    /**
     *
     */
    val asteroids: Flow<List<Asteroid>> =
        database.asteroidDao.getAsteroids().map {
            it.asDomainModel()
        }

    /**
     *
     */
    suspend fun refreshAsteroids(
        startDate: Date, endDate: Date
    ) {
        withContext(Dispatchers.IO) {
            val response = api.getNeo(startDate.formatted(SIMPLE_FORMAT), endDate.formatted(SIMPLE_FORMAT)).await()
            val asteroids = response.nearEarthObjects.values.flatten()
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }


    suspend fun removeAsteroidsBeforeToday() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.removeBeforeToday()
        }
    }
}