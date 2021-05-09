package com.udacity.asteroidradar.features.neo.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.neo.data.repository.AsteroidRepository
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import kotlinx.coroutines.flow.Flow

//class GetListAsteroid(private val repository: AsteroidRepository) :
//    UseCase<Flow<List<Asteroid>>>() {
//
//    override fun execute(): Flow<List<Asteroid>> = repository.asteroids
//}

class GetListAsteroid(private val repository: AsteroidRepository) : UseCase() {
    operator fun invoke(): Flow<List<Asteroid>> = repository.asteroids
}