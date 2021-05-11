package com.udacity.asteroidradar.features.asteroid.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.asteroid.data.repository.PictureOfDayRepository
import javax.inject.Inject

class RefreshPictureOfDay @Inject constructor(val repository: PictureOfDayRepository) : UseCase() {
    suspend operator fun invoke() = repository.refreshPictureOfDay()
}