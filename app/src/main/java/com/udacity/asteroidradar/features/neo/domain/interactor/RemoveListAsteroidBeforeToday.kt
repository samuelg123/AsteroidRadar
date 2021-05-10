package com.udacity.asteroidradar.features.neo.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.neo.data.repository.AsteroidRepository
import java.util.*
import javax.inject.Inject

class RemoveListAsteroidBeforeToday @Inject constructor(val repository: AsteroidRepository) :
    UseCase() {
    suspend operator fun invoke() =
        repository.removeAsteroidsBeforeToday()

}