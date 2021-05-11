package com.udacity.asteroidradar.features.asteroid.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.asteroid.data.repository.PictureOfDayRepository
import com.udacity.asteroidradar.features.asteroid.domain.model.PictureOfDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPictureOfDay @Inject constructor(val repository: PictureOfDayRepository) : UseCase() {
    operator fun invoke(): Flow<PictureOfDay?> = repository.pictureOfDay
}