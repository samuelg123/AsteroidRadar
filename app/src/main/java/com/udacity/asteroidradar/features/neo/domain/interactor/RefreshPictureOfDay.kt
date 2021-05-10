package com.udacity.asteroidradar.features.neo.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.neo.data.repository.AsteroidRepository
import com.udacity.asteroidradar.features.neo.data.repository.PictureOfDayRepository
import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class RefreshPictureOfDay @Inject constructor(val repository: PictureOfDayRepository) : UseCase() {
    suspend operator fun invoke() = repository.refreshPictureOfDay()
}