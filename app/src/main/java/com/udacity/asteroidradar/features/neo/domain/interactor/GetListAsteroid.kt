package com.udacity.asteroidradar.features.neo.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.neo.data.repository.AsteroidRepository
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListAsteroid @Inject constructor(val repository: AsteroidRepository) : UseCase() {
    operator fun invoke(): Flow<List<Asteroid>> = repository.asteroids
}