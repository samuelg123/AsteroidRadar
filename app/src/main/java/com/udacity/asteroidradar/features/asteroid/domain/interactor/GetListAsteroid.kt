package com.udacity.asteroidradar.features.asteroid.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.asteroid.data.repository.AsteroidRepository
import com.udacity.asteroidradar.features.asteroid.domain.model.Asteroid
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListAsteroid @Inject constructor(val repository: AsteroidRepository) : UseCase() {
    operator fun invoke(): Flow<List<Asteroid>> = repository.asteroids
}