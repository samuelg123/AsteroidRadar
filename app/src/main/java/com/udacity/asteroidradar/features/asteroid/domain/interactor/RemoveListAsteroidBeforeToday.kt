package com.udacity.asteroidradar.features.asteroid.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.asteroid.data.repository.AsteroidRepository
import javax.inject.Inject

class RemoveListAsteroidBeforeToday @Inject constructor(val repository: AsteroidRepository) :
    UseCase() {
    suspend operator fun invoke() =
        repository.removeAsteroidsBeforeToday()

}