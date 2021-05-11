package com.udacity.asteroidradar.features.asteroid.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.asteroid.data.repository.AsteroidRepository
import java.util.*
import javax.inject.Inject

class RefreshListAsteroid @Inject constructor(val repository: AsteroidRepository) : UseCase() {
    suspend operator fun invoke() {
        val calendar = Calendar.getInstance()
        val startDate = calendar.time
        val endDate = calendar.apply { add(Calendar.DAY_OF_YEAR, 7) }.time
        repository.refreshAsteroids(startDate, endDate)
    }
}